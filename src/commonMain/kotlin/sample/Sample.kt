package sample

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

expect class Sample() {
  fun checkMe(): Int
}

expect object Platform {
  fun name(): String
}

fun hello(): String = "Hello from ${Platform.name()}"

suspend fun cycle() = coroutineScope {
  delay(2000)
}

suspend fun seq(): Sequence<Int> {
  return sequence<Int> {
    var a = 0
    while (true) {
      println(1000)
      yield(a++)
    }
  }
}
