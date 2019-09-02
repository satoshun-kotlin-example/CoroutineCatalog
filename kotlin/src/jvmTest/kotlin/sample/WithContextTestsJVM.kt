package sample

import kotlinx.coroutines.*
import org.junit.Test

class WithContextTestsJVM {
  @Test
  fun www() {
    GlobalScope.launch {
      withContext(coroutineContext) {
      }
      async {  }
    }
  }

  @Test
  fun www2() {
    runBlocking {
      GlobalScope.launch {
        withContext(coroutineContext) {
        }
        async {  }
      }
      print(1)
    }
    print(100)
  }
}


fun CoroutineScope.hoge() {
  launch {
    delay(100)
  }
}
