package com.github.satoshun.example.coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import org.junit.Test

class CoroutineVariaous {
  @Test
  fun various() {
    // Flow
    flow { emit(100) }

    // channels
    Channel<Int>(Channel.RENDEZVOUS)
    Channel<Int>(Channel.UNLIMITED)
    Channel<Int>(Channel.CONFLATED)
    Channel<Int>(Channel.BUFFERED)
    Channel<Int>(10) // ArrayChannel
  }
}
