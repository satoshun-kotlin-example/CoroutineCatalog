package com.github.satoshun.example.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
  private var count = 0

  val userName = MutableLiveData<String>()

  fun serialTask() {
    viewModelScope.launch {
      val prefix = MainService.fastTask()
      val suffix = MainService.slowTask()

      userName.postValue("$prefix\n$suffix\n${count++}")
    }
  }

  fun serialTaskWithError() {
    viewModelScope.launch {
      val prefix = runCatching { MainService.fastTask() }
      val suffix = runCatching { MainService.slowTask() }
      val error = runCatching { MainService.errorTask() }

      userName.postValue("$prefix\n$suffix\n$error\n${count++}")
    }
  }

  fun serialTaskWithError2() {
    viewModelScope.launch {
      val prefix = MainService.fastTask()
      val suffix = MainService.slowTask()
      val error = MainService.errorTask()

      userName.postValue("$prefix\n$suffix\n$error\n${count++}")
    }
  }
}
