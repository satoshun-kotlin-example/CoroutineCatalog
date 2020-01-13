package com.github.satoshun.example.coroutine.flow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.satoshun.example.coroutine.MainCoroutineRule
import com.github.satoshun.example.coroutine.runBlocking
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FlowOnTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Test
  fun flowOn_normal() = coroutineRule.runBlocking {
    println("main ${Thread.currentThread()}")

    flowOf(1)
      .collect {
        println("1 ${Thread.currentThread()}")
      }

    flowOf(1)
      .flowOn(Dispatchers.Default)
      .collect {
        println("2 ${Thread.currentThread()}")
      }

    flowOf(1)
      .map {
        println("3 map1 ${Thread.currentThread()}")
        it
      }
      .flowOn(Dispatchers.Default)
      .map {
        println("3 map2 ${Thread.currentThread()}")
        it
      }
      .collect {
        println("3 ${Thread.currentThread()}")
      }
  }

  @Test
  fun rxjava() {
    println("main ${Thread.currentThread()}")
    Completable
      .complete()
      .subscribe {
        println("1 ${Thread.currentThread()}")
      }

    Single
      .just(1)
      .map {
        println("2 map ${Thread.currentThread()}")
        it
      }
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("2 ${Thread.currentThread()}")
      }) {
      }

    Thread.sleep(1000)
  }
}
