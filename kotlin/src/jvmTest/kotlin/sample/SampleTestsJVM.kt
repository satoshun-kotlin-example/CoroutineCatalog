package sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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
      foo()
      println("finish runBlockingTest")
    }
  }

  @Test
  fun launch2() {
    runBlocking {
      println("start runBlockingTest")
      foo()
      println("finish runBlockingTest")
    }
  }
}

fun CoroutineScope.foo() {
  println("start method")
  launch {
    println("start launch block")
    tooLongTask()
    println("finish launch block")
  }
  println("finish method")
}
