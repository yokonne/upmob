package com.example.upmo.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.upmo.data.models.Product
import com.example.upmo.R
import com.example.upmo.databinding.ViewProductCardBinding

class ProductAdapter(
    private val products: List<Product>,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ViewProductCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onFavoriteClick)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(
        private val binding: ViewProductCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onFavoriteClick: (Product) -> Unit) {
            // Загрузка изображения через Glide
            product.image_url?.let { url ->
                Glide.with(binding.ivProduct.context)
                    .load(url)

                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(binding.ivProduct)
            } ?: binding.ivProduct.setImageResource(R.drawable.placeholder)

            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "₽${String.format("%.2f", product.price)}"

            // Badge
            binding.tvBadge.visibility = if (product.isBestSeller) {
                binding.tvBadge.text = "BEST SELLER"
                android.view.View.VISIBLE
            } else if (product.isNew) {
                binding.tvBadge.text = "NEW!"
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }

            binding.btnAddToCart.setOnClickListener {
                // TODO: Добавить в корзину
            }

            binding.ivFavorite.setOnClickListener {
                onFavoriteClick(product)
            }
        }
    }
}