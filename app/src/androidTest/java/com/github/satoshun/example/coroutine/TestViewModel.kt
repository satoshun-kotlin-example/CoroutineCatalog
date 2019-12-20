package com.github.satoshun.example.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TestViewModel(
  private val repository: TestRepository = TestRepository()
) : ViewModel() {

  val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
  val searchResult = queryChannel
    .asFlow()
    .debounce(500)
    .mapLatest {
      "$it $it"
    }

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

  val user1 = liveData<String> {
    runCatching { repository.success() }
      .onSuccess { emit(it) }
      .onFailure { emit(it.message.toString()) }
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
