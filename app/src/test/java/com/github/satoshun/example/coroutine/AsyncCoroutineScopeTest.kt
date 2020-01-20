package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import com.github.satoshun.example.coroutine.rule.scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class AsyncCoroutineScopeTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Test
  fun launchTest() = coroutineRule.runBlocking {
    println("start")
    coroutineScope {
      coroutineRule.scope().launchException()
    }
    println("end")
  }
}

fun CoroutineScope.launchException() {
  launch {
    throw IOException("launchException")
  }
}
