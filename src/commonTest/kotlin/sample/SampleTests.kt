package sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
  @Test
  fun testMe() {
    assertTrue(Sample().checkMe() > 0)
  }

  @Test
  fun testSeq() {
    val job = GlobalScope.launch(Dispatchers.Unconfined) {
      val s = seq().take(10)
      println(s.take(1).toList())
      println(s.take(1).toList())
      println(10001)
    }
    println(10002)
  }
}
