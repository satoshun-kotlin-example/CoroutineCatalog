package com.github.satoshun.example.coroutine

import kotlinx.coroutines.delay

suspend fun simple2(user: User): Int {
    delay(1000)
    println("hello")

    delay(1001)
    println("hello2")

    delay(1002)
    println("hello3")

    return 100
}
