package sample

import kotlinx.coroutines.delay
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
  delay(10_000)
  return "finished!!"
}
