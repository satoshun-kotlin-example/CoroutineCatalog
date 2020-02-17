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
        println(1)
        throw Exception("hoge")
      }
      launch {
        delay(500)
        println("not")
      }
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope5() {
    val scope = CoroutineScope(Job())
    scope.launch {
      supervisorScope {
        launch {
          delay(100)
          println(1)
          throw Exception("hoge")
        }
        launch {
          delay(500)
          println(2)
        }
      }
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope6() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      val a = runCatching {
        supervisorScope {
          launch {
            delay(100)
            println(3)
            throw Exception("hoge")
          }
          launch {
            println(1)
            delay(50)
            println(2)
            delay(500)
            println(4)
          }
        }
      }
      println("outer finished $a")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope7() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      val a = runCatching {
        coroutineScope {
          launch {
            delay(100)
            println(3)
            throw Exception("hoge")
          }
          launch {
            println(1)
            delay(50)
            println(2)
            delay(500)
            println("not")
          }
        }
      }
      println("outer finished $a")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope8() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      val a = runCatching {
        coroutineScope {
          val a = async {
            delay(100)
            println(2)
            throw Exception("hoge")
          }
          val b = async {
            println(1)
            delay(500)
            println("not")
          }
          b.await()
          a.await()
          println("end")
        }
      }
      println("outer scope $a")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope9() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      val a = runCatching {
        supervisorScope {
          val a = async {
            delay(100)
            println(2)
            throw Exception("hoge")
          }
          val b = async {
            println(1)
            delay(500)
            println("not")
          }
          a.await()
          println("not")
          b.await()
        }
      }
      println("3 $a")
    }
    Thread.sleep(1000)
  }

  @Test
  fun scope10() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
      val a = async {
        println("b $this")
        delay(10000)
        ""
      }
      println("aa $a")
      println("a $this")

      val b = launch {
        println("c $this")
        delay(10000)
      }
      println("cc $b")

      a.await()
    }
    Thread.sleep(1000)
  }
}
