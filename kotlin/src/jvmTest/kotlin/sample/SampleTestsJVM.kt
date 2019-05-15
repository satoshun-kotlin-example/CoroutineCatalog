package sample

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import java.util.concurrent.CompletableFuture
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsJVM {
  @Test
  fun testHello() {
    assertTrue("JVM" in hello())
  }

  @Test
  fun testDoSomethingAsync() {
    val one = doSomethingAsync()
    one.thenAcceptBoth(CompletableFuture.supplyAsync {
      doSomethingElse()
    }) { one, two ->

    }.whenComplete { t, u ->
    }
  }
}

class AdvancedTest {
  @Test
  fun advanced1() {
    runBlockingTest {
      tooLongTask()
    }
  }

  @Test
  fun advanced2() {
    runBlocking {
      tooLongTask()
    }
  }
}

suspend fun tooLongTask(): String {
  delay(5_000)
  return "finished!!"
}

class LaunchTest {
  @Test
  fun launch1() {
    runBlockingTest {
      println("start runBlockingTest")
      foo1()
      println("finish runBlockingTest")
    }
  }

  @Test
  fun launch2() {
    runBlocking {
      println("start runBlockingTest")
      foo1()
      println("finish runBlockingTest")
    }
  }
}

fun CoroutineScope.foo1() {
  println("start method")
  launch {
    println("start launch block")
    tooLongTask()
    println("finish launch block")
  }
  println("finish method")
}

class AsyncTest {
  @Test
  fun async1() = runBlockingTest {
    println("start runBlockingTest")
    foo2()
    println("finish runBlockingTest")
  }

  @Test
  fun async2() = runBlocking {
    println("start runBlockingTest")
    foo2()
    println("finish runBlockingTest")
  }
}

suspend fun foo2() = coroutineScope {
  println("start method")

  val a = async {
    println("start launch block")
    tooLongTask()
    println("finish launch block")
    "finish async"
  }
  println("finish method")
  println("finish method ${a.await()}")
}

class Launch2Test {
  @Test
  fun async1() = runBlockingTest {
    println("start runBlockingTest")
    foo1()
    advanceTimeBy(5_000)
    println("finish runBlockingTest")
  }

  @Test
  fun async2() = runBlockingTest {
    println("start runBlockingTest")
    foo1()
    advanceTimeBy(4_999)
    println("finish runBlockingTest")
  }

  @Test
  fun async3() = runBlockingTest {
    println("start runBlockingTest")
    foo1()
    println("finish runBlockingTest")
  }
}

class WithTimeoutTest {
  @Test(expected = TimeoutCancellationException::class)
  fun withTimeout1() = runBlockingTest {
    fuga()
  }
}

suspend fun fuga() {
  val uncompleted = CompletableDeferred<Any>()
  withTimeout(1000) {
    uncompleted.await()
  }
}

class PauseDispatcherTest {
  @Test
  fun test() = runBlockingTest {
    pauseDispatcher()

    pauseDispatcher {
      println(3)
      foo5()
      println(4)
      runCurrent()
      println(5)
      advanceTimeBy(1_000)
      println(6)
    }
  }
}

fun CoroutineScope.foo5() {
  launch {
    println(1)
    delay(1_000)
    println(2)
  }
}
