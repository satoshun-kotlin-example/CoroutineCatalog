package com.github.satoshun.example.coroutine

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SuspendFuncTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  private val viewModel = TestViewModel()

  @Test
  fun pattern1() {
    coroutineRule.runBlocking {
      viewModel.test1()
    }
  }

  @Test
  fun pattern2() {
    coroutineRule.runBlocking {
      viewModel.test2()
    }
  }

  @Ignore("this test crash!")
  @Test
  fun pattern3() {
    coroutineRule.runBlocking {
      viewModel.test3()
    }
  }
}
