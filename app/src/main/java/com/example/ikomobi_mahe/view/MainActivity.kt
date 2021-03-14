package com.example.ikomobi_mahe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikomobi_mahe.R
import com.example.ikomobi_mahe.di.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disabling night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.title = getString(R.string.shopping)

        // Recycler view
        val adapter = ProductAdapter()
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            this.adapter = adapter
            this.layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
        }

        // Observing view model
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext)
        )[MainViewModel::class.java]

        viewModel.products.observe(this) {
            it?.let { adapter.submitList(it) }
        }
    }
}