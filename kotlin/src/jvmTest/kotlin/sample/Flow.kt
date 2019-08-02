package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class FlowTest {
  @Test
  fun test() = runBlockingTest {
    val f = flow {
      delay(100)
      emit(100)
    }

    f
      .map { it * 2 }
      .collect {
        println(it)
      }

    f
      .map { it * 2 }
      .collect {
        println(it)
      }
  }

  @Test
  fun name() = runBlockingTest {
    val f = callbackFlow {
      offer(100)
      offer(101)
    }
    f.collect {
      println(it)
    }
  }
}
