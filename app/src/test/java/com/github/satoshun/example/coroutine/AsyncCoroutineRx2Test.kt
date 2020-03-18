package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import com.github.satoshun.example.coroutine.rule.runBlocking
import io.reactivex.Single
import io.reactivex.functions.Function3
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.rx2.await
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class AsyncCoroutineRx2Test {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Test
  fun async() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      val a = Single.error<Any>(IOException("error"))
      a.await()
    }
    println(a.exceptionOrNull())
    println("end")
  }

  @Test
  fun async2() = coroutineRule.runBlocking {
    println("start")
    val a = runCatching {
      val a: Single<Nothing> = Single.zip(
        Single.error<Any>(IOException("error")),
        Single.just(19),
        Single.just(20),
        Function3 { a, b, c -> TODO() }
      )
      a.await()
    }
    println(a.exceptionOrNull())
    println("end")
  }
}
