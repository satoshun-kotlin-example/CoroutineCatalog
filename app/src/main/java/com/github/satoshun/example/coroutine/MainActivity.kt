package com.github.satoshun.example.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
//    viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
//
//    viewModel.old
//      .onEach {
//        println("HOGEFUGA: $it")
//      }
//      .launchIn(lifecycleScope)
//    lifecycleScope.launch {
//      delay(1000)
//      viewModel.source.send("test2")
//      delay(1)
//      viewModel.source.send("test")
//      delay(1000)
//      viewModel.source.send("test3")
//    }
//
//    viewModel.userName.observe(this, Observer {
//      Toast
//        .makeText(this@MainActivity, it!!, Toast.LENGTH_LONG)
//        .show()
//    })
//
//    val button: Button = findViewById(R.id.hello)
//    button.setOnClickListener {
//      viewModel.serialTask()
//    }
//
//    val button2: Button = findViewById(R.id.hello2)
//    button2.setOnClickListener {
//      viewModel.serialTaskWithError()
//    }
//
//    val button3: Button = findViewById(R.id.hello3)
//    button3.setOnClickListener {
//      viewModel.parallelTask()
//    }
//
//    val button4: Button = findViewById(R.id.hello4)
//    button4.setOnClickListener {
//      viewModel.parallelTaskWithError()
//    }
//
//    val button5: Button = findViewById(R.id.hello5)
//    button5.setOnClickListener {
//      viewModel.parallelTaskWithError2()
//    }
//
//    val button6: Button = findViewById(R.id.hello6)
//    button6.setOnClickListener {
//      viewModel.parallelTaskWithCancellation()
//    }
//
//    val f = flow {
//      val startTime = System.currentTimeMillis()
//      repeat(100) {
//        delay(1)
//        emit("$it ${System.currentTimeMillis() - startTime} ${Thread.currentThread()}")
//        println("$it emitted")
//      }
//    }
//
//    lifecycleScope.launch {
//      coroutineScope {
//        launch {
//          delay(10000)
//          println("hogehoge1")
//        }
//        launch {
//          delay(8000)
//          println("hogehoge2")
//        }
//      }
//      println("hogehoge3")
//    }
//
//    // Launch Thread: Main
//    // consumer: rapid
//    // flowOn:
////    lifecycleScope.launch {
////      Log.d("t1", "start ${Thread.currentThread()}")
////      f
////        .collect {
////          Log.d("t1", "$it : ${Thread.currentThread()}")
////        }
////      Log.d("t1", "start ${Thread.currentThread()}")
////    }
////
////    // Launch Thread: Main
////    // consumer: slow
////    // flowOn:
////    lifecycleScope.launch {
////      Log.d("t2", "start ${Thread.currentThread()}")
////      f
////        .collect {
////          delay(100)
////          Log.d("t2", "$it : ${Thread.currentThread()}")
////        }
////      Log.d("t2", "finish ${Thread.currentThread()}")
////    }
////
////    // Launch Thread: Main
////    // consumer: rapid
////    // flowOn: Dispatchers.IO
////    lifecycleScope.launch {
////      Log.d("t3", "start ${Thread.currentThread()}")
////      f
////        .flowOn(Dispatchers.IO)
////        .collect {
////          Log.d("t3", "$it : ${Thread.currentThread()}")
////        }
////      Log.d("t3", "finish ${Thread.currentThread()}")
////    }
//
//    // Launch Thread: Main
//    // consumer: slow
//    // flowOn: Dispatchers.IO
//    // buffer:
////    lifecycleScope.launch {
////      Log.d("t4", "start ${Thread.currentThread()}")
////      val currentTime = System.currentTimeMillis()
////      f
////        .buffer(Channel.BUFFERED)
////        .collect {
////          delay(100)
////          Log.d("t4", "$it : ${Thread.currentThread()} ${System.currentTimeMillis() - currentTime}")
////        }
////      Log.d("t4", "finish ${Thread.currentThread()}")
////    }
//
//    val f2 = callbackFlow {
//      val startTime = System.currentTimeMillis()
//      repeat(100) {
//        delay(10)
//        send("$it ${System.currentTimeMillis() - startTime} ${Thread.currentThread()}")
//        println("$it emitted")
//      }
//    }
//
////    lifecycleScope.launch {
////      Log.d("t4", "start ${Thread.currentThread()}")
////      val currentTime = System.currentTimeMillis()
////      f2
////        .buffer(Channel.BUFFERED)
////        .flowOn(Dispatchers.IO)
////        .collect {
////          delay(100)
////          Log.d("t4", "$it : ${Thread.currentThread()} s${System.currentTimeMillis() - currentTime}")
////        }
////      Log.d("t4", "finish ${Thread.currentThread()}")
////    }
//
//    lifecycleScope.launch {
//      Log.d("t4", "start ${Thread.currentThread()}")
//      val currentTime = System.currentTimeMillis()
//      f2
//        .conflate()
//        .flowOn(Dispatchers.IO)
//        .collect {
//          delay(100)
//          Log.d(
//            "t4",
//            "$it : ${Thread.currentThread()} s${System.currentTimeMillis() - currentTime}"
//          )
//        }
//      Log.d("t4", "finish ${Thread.currentThread()}")
//    }
//
//    supportFragmentManager
//      .beginTransaction()
//      .add(R.id.container, HogeFragment())
//      .commit()
//
//    lifecycleScope.launchWhenResumed {
//      println("hogehoge5")
//    }
//
//    lifecycleScope.launch {
//      val flow1 = flow {
//        delay(100000)
//        emit(100)
//        emit(2000)
//      }
//      val flow2 = flow {
//        delay(3000)
//        emit(100)
//        emit(2000)
//      }
//
//      flow1.combine(flow2) { a, b ->
//      }
//
//      coroutineScope {
//        awaitAll(
//          async {
//            flow1.collect {
//              print("HOGE1")
//            }
//          },
//          async {
//            flow2.collect {
//              println("HOGE2")
//            }
//          }
//        )
//      }
//      println("LAST")
//    }

    fun liveDataAndStateFlowLifecycleTest() {
      val liveData = MutableLiveData<Int>()
      val stateFlow = MutableStateFlow(-1)
      var current = 0

      // simulate background processing
      thread {
        while (current <= 50) {
          liveData.postValue(current)
          stateFlow.value = current
          current += 1
          Thread.sleep(500)
        }
      }

      lifecycleScope.launchWhenStarted {
        println("launchWhenStarted")
        stateFlow.collect {
          println("stateFlow $it")
        }
      }
      liveData.observe(owner = this) {
        println("liveData $it")
      }
    }
//    liveDataAndStateFlowLifecycleTest()

    fun sharedFlowTest() {
      val flow = MutableSharedFlow<Int>(
        replay = 0,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
      )

      lifecycleScope.launchWhenStarted {
        flow.emit(100)
        println("sharedFlowTest emitted 1")

        delay(100)

        flow.emit(200)
        println("sharedFlowTest emitted 2")

        flow.emit(300)
        println("sharedFlowTest emitted 3")
      }

      lifecycleScope.launchWhenStarted {
        flow.collect {
          println("sharedFlowTest collect $it")
          delay(3000)
        }
      }
    }
//    sharedFlowTest()

    fun singleFlowTest() {
      val flow = SingleFlow<Int>()

      lifecycleScope.launchWhenStarted {
        flow.singleCollect { println("one $it") }
      }
      lifecycleScope.launchWhenStarted {
        flow.singleCollect { println("two $it") }
      }

      println(flow.tryEmit(Event(10)))
    }
    singleFlowTest()
  }

  override fun onResume() {
    super.onResume()
//    lifecycleScope.launchWhenResumed {
//      println("hogehoge10")
//    }
  }

  override fun onPause() {
    super.onPause()
//    lifecycleScope.launchWhenResumed {
//      println("onPause1")
//    }
//    lifecycleScope.launchWhenCreated {
//      println("onPause2")
//    }
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycleScope.launchWhenResumed {
      println("hogehoge2")
      supportFragmentManager.popBackStack()
    }
  }
}

@ExperimentalCoroutinesApi
fun <T> SingleFlow(
  extraBufferCapacity: Int = 0,
  onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
): SingleFlow<T> {
  return MutableSharedFlow(
    replay = 1,
    extraBufferCapacity = extraBufferCapacity,
    onBufferOverflow = onBufferOverflow,
  )
}

@OptIn(ExperimentalCoroutinesApi::class)
typealias SingleFlow<T> = MutableSharedFlow<Event<T>>

suspend inline fun <T> SingleFlow<T>.singleCollect(
  crossinline action: suspend (value: T) -> Unit
) {
  this.filter { !it.isConsumed }
    .collect {
      it.isConsumed = true
      action(it.value)
    }
}

class Event<T>(
  val value: T,
  var isConsumed: Boolean = false
)

class HogeFragment : Fragment()
