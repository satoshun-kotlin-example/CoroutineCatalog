package com.github.satoshun.example.coroutine

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TestMapViewModelTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: TestMapViewModel

  @Before
  fun setUp() {
    viewModel = TestMapViewModel()
  }

  @Test
  fun test1() = coroutineRule.runBlocking {
    val actual = mutableListOf<String>()
    viewModel.latest.onEach {
      actual.add(it)
    }.launchIn(coroutineRule.scope())

    viewModel.source.send("test")

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(300)
    viewModel.source.send("test2")

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(1000)
    assertThat(actual).isEqualTo(listOf("latest test2"))
  }
}
