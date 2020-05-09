package com.github.satoshun.example.coroutine.flow

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StateFlowTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: ViewModelTest

  @Before
  fun setUp() {
    viewModel = ViewModelTest()
  }

  @Test
  fun nullInitialValue() {
    coroutineRule.runBlocking {
      viewModel
        .nullable
        .first()
    }
  }
}

private class ViewModelTest {
  val nullable = MutableStateFlow<Int?>(null)
}
