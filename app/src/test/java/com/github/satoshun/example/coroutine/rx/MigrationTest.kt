package com.github.satoshun.example.coroutine.rx

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class MigrationTest {
  @Test
  fun test() {
    val repository = TestRepository()

    val disposble = repository.getUser()
      .subscribeOn(Schedulers.io())
      .subscribe(
        { println(it) },
        { println(it) }
      )
  }
}

private class TestRepository {
  fun getUser(): Flowable<User> = 
    Flowable.just(User())
}

class User
