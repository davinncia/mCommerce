package com.example.ikomobi_mahe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.ikomobi_mahe.R
import com.example.ikomobi_mahe.di.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view)

        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext)
        )[MainViewModel::class.java]

        viewModel.networkAvailable.observe(this) {
            count++
            textView.text = "Network OK: $count"
        }
    }
}