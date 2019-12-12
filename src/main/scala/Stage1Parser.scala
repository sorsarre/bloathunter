import util.control.Breaks._
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.collection.immutable.Seq
import scala.util.Using
import java.nio.file.Path

object Stage1Parser {
  implicit class StringExt(s: String) {
    def stripQuotes: String = s.replaceAll("\"", "")
    def toAbsolutePath: String = Path.of(s).normalize.toAbsolutePath.toString
  }

  private def parseLine(line: String): Option[Stage1Entry] = {
    if (line.startsWith("#") && !line.startsWith("#pragma ")) {
      // here we go, now that's our client!
      val parts = line.split("\\s+").filterNot(_.isEmpty).drop(2)
      if (parts.length == 0) {
        return None
      }

      val fileName = parts(0).stripQuotes
      if (fileName == "<built-in>" || fileName == "<command") {
        return None
      }
      val flags = parts.drop(1).toSet[String]
      val isNew = flags("1")
      val isReturn = flags("2")
      val entry = new Stage1Entry(fileName, 0, isNew, isReturn)
      return Some(entry)
    }
    None
  }

  private def parseLineAndUpdateCounter(line: String)(implicit state: Stage1ParserState): Unit = {
    val entry = parseLine(line)
    if (entry.nonEmpty) {
      // new entry -- so cut off the line count for the previous one
      state.cutOff()
      // add the latest entry
      state.addEntry(entry.get)
    } else {
      state.counter += 1
    }
  }

  private def mergeEntries(entries: Seq[Stage1Entry]): Seq[Stage2Entry] = {
    entries
      .groupBy { _.fileName }
      .map { group => new Stage2Entry(group._1, group._2.foldLeft(0) { (a,b) => a+b.lines}) }
      .toSeq
  }

  def parse(filename: String): Seq[Stage2Entry] = {
    implicit val state: Stage1ParserState = new Stage1ParserState()
    Using(Source.fromFile(filename)) {
      _.getLines()
        .takeWhile(_ != null)
        .foreach { this.parseLineAndUpdateCounter(_) }
    }
    // fill up the lines for the last entry
    state.cutOff()
    val stage1 = state.entries.sortBy { _.fileName }
    mergeEntries(stage1).sortBy { _.fileName }
  }
}
