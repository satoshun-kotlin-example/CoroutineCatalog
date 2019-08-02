package com.github.satoshun.example.coroutine

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModel = ViewModelProviders.of(this).get()

    viewModel.userName.observe(this, Observer {
      Toast
        .makeText(this@MainActivity, it!!, Toast.LENGTH_LONG)
        .show()
    })

    val button: Button = findViewById(R.id.hello)
    button.setOnClickListener {
      viewModel.serialTask()
    }

    val button2: Button = findViewById(R.id.hello2)
    button2.setOnClickListener {
      viewModel.serialTaskWithError()
    }

    val button3: Button = findViewById(R.id.hello3)
    button3.setOnClickListener {
      viewModel.parallelTask()
    }

    val button4: Button = findViewById(R.id.hello4)
    button4.setOnClickListener {
      viewModel.parallelTaskWithError()
    }

    val button5: Button = findViewById(R.id.hello5)
    button5.setOnClickListener {
      viewModel.parallelTaskWithError2()
    }

    val button6: Button = findViewById(R.id.hello6)
    button6.setOnClickListener {
      viewModel.parallelTaskWithCancellation()
    }
  }
}
