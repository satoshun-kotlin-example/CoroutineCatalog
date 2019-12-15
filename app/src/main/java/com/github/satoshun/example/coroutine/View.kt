package com.github.satoshun.example.coroutine

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

val View.lifecycleOwner: LifecycleOwner
  get() = retrieveLifecycleOwner(context)

private fun retrieveLifecycleOwner(context: Context): LifecycleOwner {
  return when (context) {
    is LifecycleOwner -> context
    is ContextWrapper -> retrieveLifecycleOwner(context.baseContext)
    else -> ProcessLifecycleOwner.get()
  }
}
