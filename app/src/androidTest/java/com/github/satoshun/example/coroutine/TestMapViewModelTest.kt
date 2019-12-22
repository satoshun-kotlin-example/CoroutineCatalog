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

    coroutineRule.testDispatcher.advanceTimeBy(2000)
    assertThat(actual).isEqualTo(listOf("latest test2"))
  }

  @Test
  fun test2() = coroutineRule.runBlocking {
    val actual = mutableListOf<String>()
    val job = viewModel.old.onEach {
      actual.add(it)
    }.launchIn(coroutineRule.scope())

    assertThat(viewModel.source2.offer("test")).isTrue()

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(300)
    assertThat(viewModel.source2.offer("test2")).isFalse()

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(5000)
    assertThat(actual).isEqualTo(listOf("old test"))

    job.cancel()
  }

  @Test
  fun test3() = coroutineRule.runBlocking {
    val actual = mutableListOf<String>()
    val job = viewModel.conflate
      .onEach { actual.add(it) }
      .launchIn(coroutineRule.scope())

    assertThat(viewModel.source3.offer("test")).isTrue()

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(300)
    viewModel.source3.offer("test")

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(5000)
    assertThat(actual).isEqualTo(listOf("old test"))

    job.cancel()
  }

  @Test
  fun test4() = coroutineRule.runBlocking {
    val actual = mutableListOf<String>()
    val job = viewModel.conflate
      .onEach { actual.add(it) }
      .launchIn(coroutineRule.scope())

    assertThat(viewModel.source3.offer("test")).isTrue()

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(300)
    viewModel.source3.offer("test2")
    viewModel.source3.offer("test3")

    assertThat(actual).isEmpty()

    coroutineRule.testDispatcher.advanceTimeBy(5000)
    assertThat(actual).isEqualTo(listOf("old test", "old test3"))

    job.cancel()
  }
}
