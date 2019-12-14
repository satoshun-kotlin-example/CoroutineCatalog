package com.github.satoshun.example.coroutine

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SuspendFuncTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  private val repository = Repository()

  @Test
  fun pattern1() {
    coroutineRule.runBlocking {
      repository.test()
    }
  }
}
