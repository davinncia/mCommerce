package com.example.ikomobi_mahe.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikomobi_mahe.model.Product
import com.example.ikomobi_mahe.repository.NetworkRepository
import com.example.ikomobi_mahe.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(
    networkRepo: NetworkRepository,
    productRepo: ProductRepository
) : ViewModel() {

    val products: LiveData<List<Product>> = productRepo.products

    val networkAvailable: LiveData<Boolean> = Transformations.map(networkRepo.isNetworkAvailable) {
        // Removing callback once data has been acquired from server
        networkRepo.removeCallback()

        viewModelScope.launch {
            //TODO: catch error here
            productRepo.fetchProducts()
        }
        it
    }
}