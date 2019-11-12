package sample

val EMPTY = Test("")

data class Test(val a: String) {
  val isBlank = this == EMPTY
}
