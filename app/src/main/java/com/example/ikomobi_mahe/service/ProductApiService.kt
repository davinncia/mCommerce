package com.example.ikomobi_mahe.service

import com.example.ikomobi_mahe.model.Product
import retrofit2.http.GET

interface ProductApiService {
    //https://agf.ikomobi.fr/android-hiring-test/products.json

    @GET("android-hiring-test/products.json")
    suspend fun getAllProducts(): List<Product>
}