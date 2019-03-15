package com.github.satoshun.example.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel : ViewModel() {
  val userName = MutableLiveData<String>()

  fun serialTask() {
    viewModelScope.launch {
      val start = System.currentTimeMillis()
      val prefix = MainService.fastTask()
      val suffix = MainService.slowTask()

      userName.postValue("$prefix\n$suffix\n${System.currentTimeMillis() - start}")
    }
  }

  fun serialTaskWithError() {
    viewModelScope.launch {
      val prefix = runCatching { MainService.fastTask() }
      val suffix = runCatching { MainService.slowTask() }
      val error = runCatching { MainService.errorTask() }

      userName.postValue("$prefix\n$suffix\n$error\n")
    }
  }

  fun parallelTask() {
    viewModelScope.launch {
      val start = System.currentTimeMillis()
      val prefix = async { MainService.fastTask() }
      val suffix = async { MainService.slowTask() }

      userName.postValue("${prefix.await()}\n${suffix.await()}\n${System.currentTimeMillis() - start}")
    }
  }

  fun parallelTaskWithError() {
    viewModelScope.launch {
      val start = System.currentTimeMillis()

      val prefixTask = async { runCatching { MainService.fastTask() } }
      val suffixTask = async { runCatching { MainService.slowTask() } }
      val errorTask = async { runCatching { MainService.errorTask() } }

      val prefix = prefixTask.await()
      val suffix = suffixTask.await()
      val error = errorTask.await()
      userName.postValue(
        "$prefix\n" +
          "$suffix\n" +
          "$error\n" +
          "${System.currentTimeMillis() - start}"
      )
    }
  }

  fun parallelTaskWithError2() {
    viewModelScope.launch {
      supervisorScope {
        val start = System.currentTimeMillis()

        val prefixTask = async { MainService.fastTask() }
        val suffixTask = async { MainService.slowTask() }
        val errorTask = async { MainService.errorTask() }

        val prefix = runCatching { prefixTask.await() }
        val suffix = runCatching { suffixTask.await() }
        val error = runCatching { errorTask.await() }
        userName.postValue(
          "$prefix\n" +
            "$suffix\n" +
            "$error\n" +
            "${System.currentTimeMillis() - start}"
        )
      }
    }
  }
}
