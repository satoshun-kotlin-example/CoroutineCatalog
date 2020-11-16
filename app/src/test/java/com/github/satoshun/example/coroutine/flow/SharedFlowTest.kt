package com.github.satoshun.example.coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

class SharedFlowTest {
  @Test
  fun test(): Unit = runBlocking {
    val flow = MutableSharedFlow<Int>()

    val job = launch {
      flow.emitWaitUntilSubscription(100)

      println("launch emitWaitUntilCollector")

      flow.emitWaitUntilSubscription(1200)
    }


    println("first ${job.isCancelled}")

    println(flow.first())

    println("second ${job.isCancelled}")

    println(flow.first())

    println("third")
  }
}

suspend fun <T> MutableSharedFlow<T>.emitWaitUntilSubscription(
  value: T,
  limitRefCount: Int = 1
) {
  subscriptionCount
    .filter { it >= limitRefCount }
    .first()

  emit(value)
}
