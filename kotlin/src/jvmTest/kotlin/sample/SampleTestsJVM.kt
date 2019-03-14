package sample

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
