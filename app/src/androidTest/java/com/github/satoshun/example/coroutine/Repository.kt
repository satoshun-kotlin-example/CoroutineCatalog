package com.github.satoshun.example.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException

class TestViewModel(
  private val repository: Repository = Repository()
) : ViewModel() {
  init {
    viewModelScope.launch {
      repository
        .success1()
        .collect { println(it) }
    }

    repository
      .success1()
      .onEach { print(it) }
      .launchIn(viewModelScope)

    repository
      .exception1()
      .onEach { print(it) }
      .catch { print(it) }
      .onCompletion { }
      .launchIn(viewModelScope)
  }

  val user1 = liveData {
    val result = repository.success()
    emit(result)
  }

  val user2 = liveData<String> {
    runCatching { repository.exception() }
      .onSuccess { emit(it) }
      .onFailure { emit(it.message.toString()) }
  }

  fun test1() {
    viewModelScope.launch {
      try {
        repository.exception()
        // success
      } catch (e: Exception) {
        // failure
      }
    }
  }

  fun test2() {
    viewModelScope.launch {
      runCatching { repository.success() }
        .onSuccess { }
        .onFailure { }
    }
  }

  fun test3() {
    viewModelScope.launch {
      runCatching { repository.exception() }
        .onSuccess { println("Success") }
        .onFailure { println("Failure") }
    }
  }
}

class Repository {
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
