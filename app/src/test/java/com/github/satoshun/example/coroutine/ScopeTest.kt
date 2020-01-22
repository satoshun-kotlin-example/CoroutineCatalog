package com.github.satoshun.example.coroutine

import com.github.satoshun.example.coroutine.rule.MainCoroutineRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ScopeTest {
  @get:Rule val coroutineRule = MainCoroutineRule()

  @Test
  fun scope1() {
    val scope = CoroutineScope(Job())
    scope.launch {
      delay(100)
      throw Exception("hoge")
    }
    scope.launch {
      delay(500)
      println("finished")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope2() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      delay(100)
      throw Exception("hoge")
    }
    scope.launch {
      delay(500)
      println("finished")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope3() {
    val scope = CoroutineScope(Job())
    scope.launch {
      launch {
        delay(100)
        throw Exception("hoge")
      }
      launch {
        delay(500)
        println("finished")
      }
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope4() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      launch {
        delay(100)
        throw Exception("hoge")
      }
      launch {
        delay(500)
        println("finished")
      }
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope5() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch(SupervisorJob()) {
      launch {
        delay(100)
        throw Exception("hoge")
      }
      launch {
        delay(500)
        println("finished")
      }
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope6() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      runCatching {
        supervisorScope {
          launch {
            delay(100)
            throw Exception("hoge")
          }
          launch {
            delay(500)
            println("finished")
          }
        }
      }
      println("outer finished")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope7() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      runCatching {
        coroutineScope {
          launch {
            delay(100)
            throw Exception("hoge")
          }
          launch {
            delay(500)
            println("finished")
          }
        }
      }
      println("outer finished")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope8() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      runCatching {
        coroutineScope {
          val a = async {
            delay(100)
            throw Exception("hoge")
          }
          val b = async {
            println("start")
            delay(500)
            println("finished")
          }
          b.await()
          a.await()
        }
      }
      println("outer scope")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope9() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      runCatching {
        supervisorScope {
          val a = async {
            delay(100)
            throw Exception("hoge")
          }
          val b = async {
            println("start")
            delay(500)
            println("finished")
          }
          a.await()
          b.await()
        }
      }
      println("outer scope")
    }
    Thread.sleep(1000)
  }
}
