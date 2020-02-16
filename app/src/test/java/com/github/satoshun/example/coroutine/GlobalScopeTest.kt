package com.github.satoshun.example.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class GlobalScopeTest {
  @Test
  fun hoge() {
    println("outer ${Thread.currentThread()}")
    val job = GlobalScope.launch {
      println("inner ${Thread.currentThread()}")
    }
    Thread.sleep(50000)
  }
}
