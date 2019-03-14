package com.github.satoshun.example.coroutine

import kotlinx.coroutines.delay
import java.io.IOException

object MainService {
  suspend fun fastTask(): String {
    delay(100)
    return "finished fastTask"
  }

  suspend fun slowTask(): String {
    delay(3000)
    return "finished slowTask"
  }

  suspend fun errorTask(): String {
    delay(30)
    throw IOException("errorTask")
  }
}
