package com.github.satoshun.example.coroutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BroadcastChannelTest {
  @get:Rule val coroutineRule = MainCoroutineRule()
  @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val viewModel = TestViewModel()

  @Test
  fun user1() = coroutineRule.runBlocking {
    viewModel.result.observeForTesting {
      viewModel.queryChannel.send("test1")
      assertThat(value).isNull()
      viewModel.queryChannel.send("test2")

      coroutineRule.testDispatcher.advanceTimeBy(2000)

      val time = measureTimeMillis {
        assertThat(getOrAwaitValue()).isEqualTo("test2 test2")
      }
      println("time $time")
    }
  }
}
