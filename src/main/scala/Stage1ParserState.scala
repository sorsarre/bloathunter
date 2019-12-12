import scala.collection.mutable.ArrayBuffer

class Stage1ParserState {
  var _counter: Int = 0
  var _entries: ArrayBuffer[Stage1Entry] = new ArrayBuffer[Stage1Entry]()

  def counter: Int = _counter
  def counter_=(value: Int): Unit = {
    _counter = value
  }

  def addEntry(entry: Stage1Entry): Unit = {
    _entries.addOne(entry)
  }

  def reset(): Unit = {
    _counter = 0
    _entries.clear()
  }

  def cutOff(): Unit = {
    if (_entries.nonEmpty) {
      _entries.last.lines = this._counter
    }
    this._counter = 0
  }

  def entries: Seq[Stage1Entry] = _entries.toSeq
}
