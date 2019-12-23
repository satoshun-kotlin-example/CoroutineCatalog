package com.github.satoshun.example.coroutine.channels

import com.github.satoshun.example.coroutine.MainCoroutineRule
import com.github.satoshun.example.coroutine.runBlocking
import com.github.satoshun.example.coroutine.scope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChannelTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  // - conflated
  @Test
  fun test1() = coroutineRule.runBlocking {
    val channel = Channel<String>(Channel.CONFLATED)

    coroutineRule.scope().launch {
      channel.consumeAsFlow().collect {
        println("1 $it")
      }
    }

    coroutineRule.scope().launch {
      channel.consumeAsFlow().collect {
        println("2 $it")
      }
    }

    channel.send("test1")
    channel.send("test2")
    channel.send("test3")
    channel.send("test4")
  }
}
