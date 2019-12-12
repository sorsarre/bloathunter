class Stage2Entry(val fileName: String, val properLines: Int, val cumulativeLines: Int) {
  override def toString: String = s"[Stage2 Entry fileName=$fileName, lines=$properLines]"
}
