package com.github.satoshun.example.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Test

class WithContextTest {
  @Test
  fun noncancelable() {
    val job = GlobalScope.launch {
      println("outer 1")
      withContext(NonCancellable) {
        println("withContext1(NonCancellable)")
        delay(500)
        println("withContext2(NonCancellable)")
      }
      delay(100)
      println("outer 2")
    }
    job.cancel()
    Thread.sleep(500000)
  }
}
