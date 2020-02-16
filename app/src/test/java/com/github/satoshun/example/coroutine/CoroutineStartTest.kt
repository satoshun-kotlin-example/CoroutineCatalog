package com.github.satoshun.example.coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

class CoroutineStartTest {
  @Test
  fun default() {
    println("outer ${Thread.currentThread()}")
    val job = GlobalScope.launch(start = CoroutineStart.DEFAULT) {
      println("inner ${Thread.currentThread()}")
    }
    println(job.start())
    Thread.sleep(500)
  }

  @Test
  fun lazy() {
    println("outer lazy ${Thread.currentThread()}")
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
      println("inner lazy ${Thread.currentThread()}")
    }
    println(job.start())
    Thread.sleep(500)
  }

  @Test
  fun undispatched() {
    println("outer undispatched ${Thread.currentThread()}")
    val job = GlobalScope.launch(
      context = Dispatchers.Unconfined,
      start = CoroutineStart.UNDISPATCHED
    ) {
      println("inner undispatched ${Thread.currentThread()}")
      delay(10)
      println("inner undispatched2 ${Thread.currentThread()}")
    }
    println(job.start())
    Thread.sleep(500)
  }

  @Test
  fun atomic() {
    println("outer atomic ${Thread.currentThread()}")
    val job = GlobalScope.launch(
      context = Dispatchers.IO,
      start = CoroutineStart.ATOMIC
    ) {
      println("inner atomic ${Thread.currentThread()}")
      delay(100)
      println("inner atomic2 ${Thread.currentThread()}")
    }
    println(job.cancel())
    Thread.sleep(500)
  }

  @Test
  fun default2() {
    println("outer ${Thread.currentThread()}")
    val job = GlobalScope.launch(
      context = Dispatchers.IO,
      start = CoroutineStart.DEFAULT
    ) {
      println("inner ${Thread.currentThread()}")
      delay(100)
      println("inner2 ${Thread.currentThread()}")
    }
    println(job.cancel())
    Thread.sleep(500)
  }
}
