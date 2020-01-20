package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import com.github.satoshun.example.coroutine.rule.scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class AsyncCoroutineScopeTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Ignore("throw exception")
  @Test
  fun launchTest() = coroutineRule.runBlocking {
    println("start")
    runCatching { coroutineRule.scope().launchException() }
    println("end")
  }

  @Test
  fun launchTest2() = coroutineRule.runBlocking {
    println("start")
    runCatching { launchException2() }
    println("end")
  }
}

fun CoroutineScope.launchException() {
  launch {
    throw IOException("launchException")
  }
}

suspend fun launchException2() = suspendCancellableCoroutine<Unit> { cont ->
  cont.resumeWithException(IOException("launchException"))
//  throw IOException("launchException")
}
