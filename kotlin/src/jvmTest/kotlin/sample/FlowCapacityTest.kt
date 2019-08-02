package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowCapacityTest {
  @Test
  fun a() = runBlocking {
    val f = flow {
      emit(1)
      delay(100)
      emit(2)
    }

    f
      .collect {
        println(it)
      }

    println("finish")
  }
}
