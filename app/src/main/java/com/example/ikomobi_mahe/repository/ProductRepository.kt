package com.example.ikomobi_mahe.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ikomobi_mahe.database.AppDatabase
import com.example.ikomobi_mahe.model.Product
import com.example.ikomobi_mahe.service.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository in charge of acquiring the list of products from the server or cache if there is no connection available.
 */
class ProductRepository(context: Context) {

    // Retrofit
    private val url = "https://agf.ikomobi.fr/"
    private val retrofit =
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    private val service = retrofit.create(ProductApiService::class.java)

    // Database
    private val db = AppDatabase.getDatabase(context.applicationContext)
    private val dao = db.productDao()

    val products: LiveData<List<Product>> = dao.getAll()


    suspend fun fetchProducts() {

        try {
            val response = service.getAllProducts()
            // Caching response and updating exercises by observation
            dao.insert(response)
        } catch (cause: Throwable) {
            Log.w(ProductRepository::class.java.name, "Error connecting server", cause)
        }

    }
}