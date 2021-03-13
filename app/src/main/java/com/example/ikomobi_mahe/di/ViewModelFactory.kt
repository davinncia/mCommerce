package com.example.ikomobi_mahe.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ikomobi_mahe.repository.NetworkRepository
import com.example.ikomobi_mahe.repository.ProductRepository
import com.example.ikomobi_mahe.view.MainViewModel
import java.lang.IllegalArgumentException

/**
 * Factory to create view models using manual dependency injection.
 */
class ViewModelFactory
private constructor(appContext: Context) : ViewModelProvider.Factory {

    private val networkRepo = NetworkRepository.getInstance(appContext)
    private val productRepo = ProductRepository(appContext)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                networkRepo,
                productRepo
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class.")
        }
    }

    // Singleton
    companion object {
        private var sFactory: ViewModelFactory? = null

        fun getInstance(appContext: Context): ViewModelFactory {
            if (sFactory == null) {
                synchronized(ViewModelFactory) {
                    if (sFactory == null) {
                        sFactory = ViewModelFactory(appContext)
                    }
                }
            }
            return sFactory!!
        }
    }
}