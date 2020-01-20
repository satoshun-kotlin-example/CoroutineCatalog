package com.github.satoshun.example.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    runBlocking {
      withContext(Dispatchers.IO) {
        delay(500)

        println(10000)
      }

      println(10)
    }
    println(1000)
  }

  @Test
  fun courutine() {
    val scope = CoroutineScope(Dispatchers.Main).launch { }
  }
}
