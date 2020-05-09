package com.github.satoshun.example.coroutine.flow

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import org.junit.Rule
import org.junit.Test

class StateFlowTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Test
  fun noInitialValue() {
  }
}
