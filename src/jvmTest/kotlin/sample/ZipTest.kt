package sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class ZipTest {
  private val scope get() = GlobalScope

  @Test
  fun serialZip() {
    val p1 = scope.launch {
      val r1 = fastTask()
      val r2 = slowTask()
      val r3 = errorTask()

      println("p1 unreachable")
    }

    // wrap with Result type
    val p2 = scope.launch {
      val r1 = runCatching { fastTask() }
      val r2 = runCatching { slowTask() }
      val r3 = runCatching { errorTask() }

      println("p2 reachable")
    }

    runBlocking {
      p1.join()
      p2.join()

      println("runBlocking reachable")
    }
  }
}

private suspend fun fastTask(): String {
  delay(5)
  return "finish fastTask"
}

private suspend fun slowTask(): String {
  delay(1000)
  return "finish slowTask"
}

private suspend fun errorTask(): String {
  delay(25)
  if (true) {
    throw IOException("error sample3")
  }
  return "unreachable"
}
