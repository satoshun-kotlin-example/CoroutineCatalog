package sample

import kotlinx.coroutines.Deferred

suspend inline fun <T1, T2> zip(t1: Deferred<T1>, t2: Deferred<T2>): Pair<T1, T2> {
  return t1.await() to t2.await()
}

suspend inline fun <T1, T2> Deferred<T1>.zipWith(t2: Deferred<T2>): Pair<T1, T2> {
  return await() to t2.await()
}
