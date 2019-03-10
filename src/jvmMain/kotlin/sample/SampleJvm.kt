package sample

import java.util.concurrent.CompletableFuture

actual class Sample {
  actual fun checkMe() = 42
}

actual object Platform {
  actual fun name(): String = "JVM"
}

fun doSomethingAsync(): CompletableFuture<Int> {
  TODO()
}

fun doSomethingElse(): Int {
  TODO()
}
