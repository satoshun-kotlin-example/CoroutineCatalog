package sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
}


fun CoroutineScope.hoge() {
  launch {
    delay(100)
  }
}
