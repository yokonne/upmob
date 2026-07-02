package com.example.upmo.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.upmo.R
import com.example.upmo.data.models.Product

class PromotionAdapter(
    private val promotions: List<Product>
) : RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_promotion, parent, false)
        return PromotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(promotions[position])
    }

    override fun getItemCount(): Int = promotions.size

    inner class PromotionViewHolder(itemView: android.view.View) :
        RecyclerView.ViewHolder(itemView) {

        private val ivPromotion: android.widget.ImageView = itemView.findViewById(R.id.ivPromotion)
        private val tvTitle: android.widget.TextView = itemView.findViewById(R.id.tvPromotionTitle)
        private val tvDiscount: android.widget.TextView = itemView.findViewById(R.id.tvDiscount)

        fun bind(product: Product) {
            ivPromotion.setImageResource(product.imageRes)
            tvTitle.text = product.name
            tvDiscount.text = "₽${String.format("%.2f", product.price)} OFF"
        }
    }
}