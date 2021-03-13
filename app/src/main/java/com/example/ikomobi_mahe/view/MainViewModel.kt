package com.example.ikomobi_mahe.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ikomobi_mahe.repository.NetworkRepository

class MainViewModel(networkRepo: NetworkRepository) : ViewModel() {

    val networkAvailable: LiveData<Boolean> = Transformations.map(networkRepo.isNetworkAvailable) {
        networkRepo.removeCallback()
        it
    }
}