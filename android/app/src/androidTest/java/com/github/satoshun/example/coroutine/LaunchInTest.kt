package com.github.satoshun.example.coroutine

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LaunchInTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Ignore("crash")
  @Test
  fun onEach_catch1() {
    val job = flowOf(1, 2, 3)
      .onEach { if (it == 1) throw IOException("test") }
      .launchIn(coroutineRule.scope())

    coroutineRule.runBlocking { job.join() }
    println("finished")
  }

  @Test
  fun onEach_catch2() {
    val job = flowOf(1, 2, 3)
      .onEach { if (it == 1) throw IOException("test") }
      .catch { Log.e("catch2", "crash!!") }
      .launchIn(coroutineRule.scope())

    coroutineRule.runBlocking { job.join() }
    Log.e("catch2", "finished")
  }

  @Test
  fun source_catch() {
    val job = flow {
      emit(1)
      emit(2)
      throw IOException("test")
      emit(3)
    }
      .onEach { Log.e("source", "$it") }
      .catch { Log.e("source", "crash!!") }
      .launchIn(coroutineRule.scope())

    coroutineRule.runBlocking { job.join() }
    Log.e("source", "finished")
  }

  @Test
  fun source_catch2() {
    val job = flow {
      emit(1)
      emit(2)
      throw IOException("test")
      emit(3)
    }
      .onEach { Log.e("source", "$it") }
      .launchIn(coroutineRule.scope())

    coroutineRule.runBlocking { job.join() }
    Log.e("source", "finished")
  }
}
