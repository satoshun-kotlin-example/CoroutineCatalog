package com.github.satoshun.example.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TestRepository {
  suspend fun success(): String {
    delay(1000)
    return "test"
  }

  suspend fun exception(): String {
    delay(1000)
    throw IOException("test2")
  }

  fun success1(): Flow<String> = flow {
    delay(1000)
    emit("1")
    delay(2000)
    emit("2")
  }

  fun exception1(): Flow<String> = flow {
    throw IOException("exception")
  }
}
