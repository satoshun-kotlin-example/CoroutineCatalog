package com.github.satoshun.example.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class TestViewModel(
  private val repository: Repository = Repository()
) : ViewModel() {
  fun test1() {
    viewModelScope.launch {
      try {
        repository.test1()
      } catch (e: Exception) {
      }
    }
  }

  fun test2() {
    viewModelScope.launch {
      repository.test1()
    }
  }

  fun test3() {
    viewModelScope.launch {
      repository.test2()
    }
  }
}

class Repository {
  suspend fun test1(): String {
    delay(1000)
    return "test"
  }

  suspend fun test2(): String {
    delay(1000)
    throw IOException("test2")
  }
}
