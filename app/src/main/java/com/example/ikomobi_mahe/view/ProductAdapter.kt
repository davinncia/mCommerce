package com.example.ikomobi_mahe.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ikomobi_mahe.R
import com.example.ikomobi_mahe.model.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameView = itemView.findViewById<TextView>(R.id.tv_product_name_item)
        private val priceView = itemView.findViewById<TextView>(R.id.tv_product_price_item)
        private val imageView = itemView.findViewById<ImageView>(R.id.iv_product_item)

        fun bind(product: Product) {
            nameView.text = product.name
            priceView.text = "€ ${product.price}"
            val url = product.imageUrl.replace("http:", "https:") // ClearText traffic not permitted
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }
}