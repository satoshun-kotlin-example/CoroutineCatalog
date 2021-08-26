package com.github.satoshun.example.coroutine

import kotlinx.coroutines.delay

suspend fun cancelTest(): String {
  delay(100000)
  return "abc"
}

suspend fun cancelTest2(): String {
  runCatching { delay(100000) }
  return "abc"
}
