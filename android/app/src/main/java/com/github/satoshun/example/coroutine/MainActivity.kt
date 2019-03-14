package com.github.satoshun.example.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel.userName.observe(this, Observer {
    })

    viewModel = ViewModelProviders.of(this).get()
    viewModel.doSomething()
  }
}

class MainViewModel : ViewModel() {
  val userName = MutableLiveData<String>()

  fun doSomething() {
    viewModelScope.launch {
      val prefix = MainService.fastTask()
      val suffix = MainService.slowTask()

      userName.postValue("$prefix $suffix")
    }
  }
}

object MainService {
  suspend fun fastTask(): String {
    delay(10)
    return "finished fastTask"
  }

  suspend fun slowTask(): String {
    delay(1000)
    return "finished slowTask"
  }

  suspend fun errorTask(): String {
    delay(30)
    throw IOException("errorTask")
  }
}
