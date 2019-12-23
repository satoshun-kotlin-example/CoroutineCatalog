package com.github.satoshun.example.coroutine.flow

import com.github.satoshun.example.coroutine.MainCoroutineRule
import com.github.satoshun.example.coroutine.runBlocking
import com.github.satoshun.example.coroutine.scope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TerminalTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: TerminalViewModel

  @Before
  fun setUp() {
    viewModel = TerminalViewModel()
  }

  @Test
  fun test() = coroutineRule.runBlocking {
    // collect and some variants
    viewModel.values.collect {
      delay(100)
      println("collect $it")
    }
    viewModel.values.collectIndexed { index, value ->
      delay(100)
      println("collectIndexed $index $value")
    }
    viewModel.values.collectLatest {
      delay(100)
      println("collectLatest $it")
    }

    // launchIn + onEach and some ops
    viewModel
      .values
      .onEach { println("launchIn $it") }
      .launchIn(coroutineRule.scope())

    // collections
    println("toList ${viewModel.values.toList()}")
    println("toSet ${viewModel.values.toSet()}")
    val mutable = mutableListOf<String>()
    println("toCollection ${viewModel.values.toCollection(mutable)}")

    // count up
    println("count ${viewModel.values.count()}")

    // first
    println("first ${viewModel.values.first()}")

    // single
    println("single ${viewModel.values.take(1).single()}")
    println("single ${viewModel.values.take(1).singleOrNull()}")

    // reduce
    val result = viewModel.values.reduce { accumulator, value ->
      accumulator + value
    }
    println("reduce $result")

    // fold
    val fold = viewModel.values.fold(0) { acc, value ->
      acc + value.toInt()
    }
    println("fold $fold")
  }
}
