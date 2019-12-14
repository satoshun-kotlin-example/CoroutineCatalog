package com.github.satoshun.example.coroutine

import kotlinx.coroutines.delay

class Repository {
  suspend fun test(): String {
    delay(1000)
    return "test"
  }
}
