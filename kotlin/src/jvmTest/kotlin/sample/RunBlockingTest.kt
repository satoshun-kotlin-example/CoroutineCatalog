package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RunBlockingTest {
  @Test
  fun test() {
    runBlocking {
      val job = launch {
        delay(100)
        println("World!")
      }
      println("Hello, ")
    }
  }
}
