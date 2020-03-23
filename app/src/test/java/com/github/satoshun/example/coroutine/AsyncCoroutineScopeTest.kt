package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
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
  fun launch1() = coroutineRule.runBlocking {
    println("start")
    runCatching {
      launch { throw IOException("launchException") }
    }
    println("end")
  }

  @Test
  fun launch2() = coroutineRule.runBlocking {
    println("start")
    runCatching { launchException2() }
    println("end")
  }

  @Test
  fun launch3() = coroutineRule.runBlocking {
    println("start")
    runCatching {
      coroutineScope {
        launch { throw IOException("launchException") }
      }
    }
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

  @Ignore("throw exception")
  @Test
  fun async3() = coroutineRule.runBlocking {
    println("start")
    val result = runCatching {
      val a = async<String> { throw IOException("async error") }
      a.await()
    }
    print(result.exceptionOrNull())
    println("end")
  }

  @Test
  fun async4() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      coroutineScope {
        val a = async<String> { throw IOException("async error") }
        a.await()
      }
    }
    println(a.exceptionOrNull())
    println("end")
  }

  @Test
  fun async5() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      coroutineScope {
        val a = async<String> { throw IOException("async error") }
      }
    }
    println(a.exceptionOrNull())
    println("end")
  }

  @Test
  fun async6() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      supervisorScope {
        val a = async<String> { throw IOException("async error") }
      }
    }
    println(a.exceptionOrNull())
    println("end")
  }

  @Test
  fun async7() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      withContext(Dispatchers.IO) {
        val a = async<String> { throw IOException("async error") }
      }
    }
    println(a.exceptionOrNull())
    println("end")
  }

  @Test
  fun async8() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      withContext(Dispatchers.IO) {
        val a = async<String> { throw IOException("async error") }
        a.await()
      }
    }
    println(a.exceptionOrNull())
    println("end")
  }
}

suspend fun launchException2() = suspendCancellableCoroutine<Unit> { cont ->
  cont.resumeWithException(IOException("launchException"))
}
