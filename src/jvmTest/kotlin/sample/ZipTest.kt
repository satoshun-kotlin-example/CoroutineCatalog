package sample

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException
import kotlin.system.measureTimeMillis

class ZipTest {
  @Test
  fun zip() {
    val time = measureTimeMillis {
      runBlocking {
        sample1()
        sample2()
      }
    }
    println(time)
  }

  @Test
  fun zip1() {
    val time = measureTimeMillis {
      runBlocking {
        val p1 = async {
          sample1()
          println("finish1")
        }
        val p2 = async {
          sample2()
          println("finish2")
        }
        val p3 = async {
          val result = runCatching { sample3() }
          println("finish3")
          result
        }
        awaitAll(p1, p2, p3)
      }
    }
    println(time)
  }

  @Test
  fun zip2() {
    val time = measureTimeMillis {
      runBlocking {
        val p1 = async {
          sample1()
          println("finish1")
        }
        val p2 = async {
          sample2()
          println("finish2")
        }
        val p3 = async {
          val result = runCatching { sample3() }
          println("finish3")
          result
        }
        println(p1.await())
        println(p2.await())
        println(p3.await())
      }
    }
    println(time)
  }

  @Test
  fun zip3() {
    val time = measureTimeMillis {
      runBlocking {
        val p1 = async {
          sample1()
          println("finish1")
        }
        val p2 = async {
          sample2()
          println("finish2")
        }
        val p3 = async {
          sample3()
          println("finish3")
        }
        p1.await()
        p2.await()
        p3.await()
      }
    }
    println(time)
  }
}

private suspend fun sample1(): String {
  delay(32)
  return "sample1"
}

private suspend fun sample2(): String {
  delay(20)
  return "sample2"
}

private suspend fun sample3(): String {
  delay(25)
  throw IOException("error sample3")
}
