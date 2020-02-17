package com.github.satoshun.example.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ExceptionTest {
  @Test
  fun launchTest() = runBlocking {
    val job = GlobalScope.launch(context = Dispatchers.IO) {
      println("Throwing exception from launch")
      throw ArithmeticException()
    }
    job.join()
  }

  @Test
  fun launchTest2() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
      println("Caught $exception")
    }
    val job = GlobalScope.launch(context = Dispatchers.IO + handler) {
      throw ArithmeticException()
    }
    job.join()
  }

  @Test
  fun asyncTest() = runBlocking {
    val job = GlobalScope.async(context = Dispatchers.IO) {
      throw ArithmeticException()
    }
    try {
      job.await()
    } catch (e: ArithmeticException) {
      println("catch $e")
    }
    Unit
  }

  @Test
  fun asyncTest2() = runBlocking {
    val job = GlobalScope.async(context = Dispatchers.IO) {
      throw ArithmeticException()
    }
    try {
      job.await()
    } catch (e: ArithmeticException) {
      println("catch $e")
    }
    Unit
  }
}
