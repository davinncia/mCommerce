package com.example.ikomobi_mahe.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Repository informing when network connection is available.
 */
class NetworkRepository private constructor(context: Context) {

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    private val networkRequest = NetworkRequest.Builder().build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isNetworkAvailable.postValue(true)
        }
    }

    private val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun removeCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    companion object {
        // Singleton pattern
        private var INSTANCE: NetworkRepository? = null

        fun getInstance(context: Context): NetworkRepository {
            if (INSTANCE == null) {
                synchronized(NetworkRepository) {
                    if (INSTANCE == null) {
                        INSTANCE = NetworkRepository(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}