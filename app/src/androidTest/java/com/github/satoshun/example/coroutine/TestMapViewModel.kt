package com.github.satoshun.example.coroutine

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class TestMapViewModel {
  val source = BroadcastChannel<String>(Channel.CONFLATED)

  val latest = source.asFlow()
    .mapLatest {
      delay(1000)
      "latest $it"
    }

  val old = source.asFlow()
    .map {
      delay(1000)
      "old $it"
    }
}
