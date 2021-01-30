package com.github.satoshun.example.coroutine.rx

import io.reactivex.Single
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
  fun getUser(): Single<User> =
    Single.just(User())
}

class User
