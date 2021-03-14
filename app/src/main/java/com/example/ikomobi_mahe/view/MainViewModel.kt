package com.example.ikomobi_mahe.view

import androidx.lifecycle.*
import com.example.ikomobi_mahe.model.Product
import com.example.ikomobi_mahe.repository.NetworkRepository
import com.example.ikomobi_mahe.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(
    networkRepo: NetworkRepository,
    productRepo: ProductRepository
) : ViewModel() {

    /**
     * List of products to be displayed. MediatorLiveData with two sources:
     * - changes in database
     * - available connection to network.
     */
    private val _products = MediatorLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        _products.addSource(productRepo.products) {
            _products.value = it
        }

        _products.addSource(networkRepo.isNetworkAvailable) { available ->
            if (available) {
                viewModelScope.launch {
                    productRepo.fetchProducts()
                }
                // Removing callback once data has been acquired from server
                networkRepo.removeCallback()
                // Removing network as source
                _products.removeSource(networkRepo.isNetworkAvailable)
            }
        }
    }

}