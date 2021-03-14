package com.example.ikomobi_mahe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("image")
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)