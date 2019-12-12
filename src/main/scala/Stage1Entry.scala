class Stage1Entry(
                   val fileName: String,
                   var lines: Int,
                   val isNew: Boolean,
                   val isReturn: Boolean) {

  override def toString: String = {
    s"[Stage1.Entry fileName=$fileName, lines=$lines, isNew=$isNew, isReturn=$isReturn]"
  }
}
