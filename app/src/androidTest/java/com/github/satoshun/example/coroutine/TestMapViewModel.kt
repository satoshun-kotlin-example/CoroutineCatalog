package com.github.satoshun.example.coroutine

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.withIndex

class TestMapViewModel {
  val source = BroadcastChannel<String>(Channel.CONFLATED)

  val latest = source.asFlow()
    .mapLatest {
      delay(1000)
      "latest $it"
    }

  val source2 = Channel<String>(Channel.RENDEZVOUS)
  val old = source2.consumeAsFlow()
    .map {
      delay(1000)
      "old $it"
    }

  val source3 = BroadcastChannel<String>(Channel.CONFLATED)
  val conflate = source3.asFlow()
    .distinctUntilChanged()
    .withIndex()
    .map {
      delay(1000)
      println(it)
      "old ${it.value}"
    }
}
