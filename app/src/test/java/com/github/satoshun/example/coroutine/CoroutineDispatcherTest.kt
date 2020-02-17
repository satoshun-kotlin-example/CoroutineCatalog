package com.github.satoshun.example.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

class CoroutineDispatcherTest {
  @Test
  fun hoge() {
    GlobalScope.launch(context = Dispatchers.IO) {
      println("inner ${Thread.currentThread()}")
      println("inner ${coroutineContext[ContinuationInterceptor]}")
    }
    Thread.sleep(500)
  }
}
