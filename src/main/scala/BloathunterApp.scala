import java.time.Clock

object BloathunterApp {
  def measure[A](fn: => A): (A, Long) = {
    val t0 = System.nanoTime
    val result = fn
    val t1 = System.nanoTime
    (result, t1-t0)
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    if (args.length > 0) {
      println("Trying to parse!")
      val result = measure { Stage1Parser.parse(args(0)) }
      val entries = result._1
      val time = result._2.toDouble / 1.0e6
      val projected = 2800
      entries.foreach(println(_))
      println(s"Done! Parsed in ${time}ms, projected over $projected source files: ${projected*time/1000.0}s")
      val totalLines = entries.foldLeft(0) { (a,b) => a+b.lines }
      println(s"Total lines: $totalLines")
    }
  }
}
