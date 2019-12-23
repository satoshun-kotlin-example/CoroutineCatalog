package com.github.satoshun.example.coroutine.flow

import kotlinx.coroutines.flow.flow

class TerminalViewModel {
  val values = flow {
    emit("1")
    emit("2")
  }
}
