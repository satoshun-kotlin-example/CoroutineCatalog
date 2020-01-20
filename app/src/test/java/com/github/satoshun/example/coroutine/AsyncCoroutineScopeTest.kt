package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
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
    runCatching { launchException() }
    println("end")
  }

  @Test
  fun launchTest2() = coroutineRule.runBlocking {
    println("start")
    runCatching { launchException2() }
    println("end")
  }

  @Ignore("throw exception")
  @Test
  fun async() = coroutineRule.runBlocking {
    println("start")
    val a = async { throw IOException("async error") }
    println("end")
  }

  @Test
  fun async2() = coroutineRule.runBlocking {
    println("start")
    val a = async { runCatching { throw IOException("async error") } }
    a.await()
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
