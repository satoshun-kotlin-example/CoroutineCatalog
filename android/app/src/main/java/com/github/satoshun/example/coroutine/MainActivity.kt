package com.github.satoshun.example.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
<<<<<<< HEAD
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
||||||| merged common ancestors
=======
import androidx.lifecycle.lifecycleScope
>>>>>>> use launchWhenResumed

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModel = ViewModelProviders.of(this).get()

    viewModel.userName.observe(this, Observer {
      Toast
        .makeText(this@MainActivity, it!!, Toast.LENGTH_LONG)
        .show()
    })

    val button: Button = findViewById(R.id.hello)
    button.setOnClickListener {
      viewModel.serialTask()
    }

    val button2: Button = findViewById(R.id.hello2)
    button2.setOnClickListener {
      viewModel.serialTaskWithError()
    }

    val button3: Button = findViewById(R.id.hello3)
    button3.setOnClickListener {
      viewModel.parallelTask()
    }

    val button4: Button = findViewById(R.id.hello4)
    button4.setOnClickListener {
      viewModel.parallelTaskWithError()
    }

    val button5: Button = findViewById(R.id.hello5)
    button5.setOnClickListener {
      viewModel.parallelTaskWithError2()
    }

    val button6: Button = findViewById(R.id.hello6)
    button6.setOnClickListener {
      viewModel.parallelTaskWithCancellation()
    }
<<<<<<< HEAD

    val f = flow {
      val startTime = System.currentTimeMillis()
      repeat(100) {
        delay(1)
        emit("$it ${System.currentTimeMillis() - startTime} ${Thread.currentThread()}")
        println("$it emitted")
      }
    }

    // Launch Thread: Main
    // consumer: rapid
    // flowOn:
//    lifecycleScope.launch {
//      Log.d("t1", "start ${Thread.currentThread()}")
//      f
//        .collect {
//          Log.d("t1", "$it : ${Thread.currentThread()}")
//        }
//      Log.d("t1", "start ${Thread.currentThread()}")
//    }
//
//    // Launch Thread: Main
//    // consumer: slow
//    // flowOn:
//    lifecycleScope.launch {
//      Log.d("t2", "start ${Thread.currentThread()}")
//      f
//        .collect {
//          delay(100)
//          Log.d("t2", "$it : ${Thread.currentThread()}")
//        }
//      Log.d("t2", "finish ${Thread.currentThread()}")
//    }
//
//    // Launch Thread: Main
//    // consumer: rapid
//    // flowOn: Dispatchers.IO
//    lifecycleScope.launch {
//      Log.d("t3", "start ${Thread.currentThread()}")
//      f
//        .flowOn(Dispatchers.IO)
//        .collect {
//          Log.d("t3", "$it : ${Thread.currentThread()}")
//        }
//      Log.d("t3", "finish ${Thread.currentThread()}")
//    }

    // Launch Thread: Main
    // consumer: slow
    // flowOn: Dispatchers.IO
    // buffer:
    lifecycleScope.launch {
      Log.d("t4", "start ${Thread.currentThread()}")
      val currentTime = System.currentTimeMillis()
      f
        .buffer(Channel.BUFFERED)
        .collect {
          delay(100)
          Log.d("t4", "$it : ${Thread.currentThread()} ${System.currentTimeMillis() - currentTime}")
        }
      Log.d("t4", "finish ${Thread.currentThread()}")
    }

    val f2 = callbackFlow {
      val startTime = System.currentTimeMillis()
      repeat(100) {
        delay(10)
        send("$it ${System.currentTimeMillis() - startTime} ${Thread.currentThread()}")
        println("$it emitted")
      }
    }

//    lifecycleScope.launch {
//      Log.d("t4", "start ${Thread.currentThread()}")
//      val currentTime = System.currentTimeMillis()
//      f2
//        .buffer(Channel.BUFFERED)
//        .flowOn(Dispatchers.IO)
//        .collect {
//          delay(100)
//          Log.d("t4", "$it : ${Thread.currentThread()} s${System.currentTimeMillis() - currentTime}")
//        }
//      Log.d("t4", "finish ${Thread.currentThread()}")
//    }
||||||| merged common ancestors
=======

    supportFragmentManager
      .beginTransaction()
      .add(R.id.container, HogeFragment())
      .commit()

    lifecycleScope.launchWhenResumed {
      println("hogehoge5")
    }
  }

  override fun onResume() {
    super.onResume()
    lifecycleScope.launchWhenResumed {
      println("hogehoge10")
    }
  }

  override fun onPause() {
    super.onPause()
    lifecycleScope.launchWhenResumed {
      println("onPause1")
    }
    lifecycleScope.launchWhenCreated {
      println("onPause2")
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycleScope.launchWhenResumed {
      println("hogehoge2")
      supportFragmentManager.popBackStack()
    }
>>>>>>> use launchWhenResumed
  }
}


class HogeFragment : Fragment()
