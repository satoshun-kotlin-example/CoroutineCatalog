package com.github.satoshun.example.coroutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LiveDataBuilderTest {
  @get:Rule val coroutineRule = MainCoroutineRule()
  @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val viewModel = TestViewModel()

  @Test
  fun user1() {
    coroutineRule.runBlocking {
      assertThat(viewModel.user1.getOrAwaitValue()).isNotNull()
    }
  }

  @Test
  fun user2() {
    coroutineRule.runBlocking {
      assertThat(viewModel.user2.getOrAwaitValue()).isEqualTo("test2")
    }
  }
}
