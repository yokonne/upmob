package com.example.upmo.ui.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.upmo.R
import com.example.upmo.data.models.Product

class ProductCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val ivProduct: ImageView
    private val tvName: TextView
    private val tvPrice: TextView
    private val tvBadge: TextView
    private val ivFavorite: ImageView
    private val btnAddToCart: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_product_card, this, true)

        ivProduct = findViewById(R.id.ivProduct)
        tvName = findViewById(R.id.tvProductName)
        tvPrice = findViewById(R.id.tvProductPrice)
        tvBadge = findViewById(R.id.tvBadge)
        ivFavorite = findViewById(R.id.ivFavorite)
        btnAddToCart = findViewById(R.id.btnAddToCart)
    }

    fun bind(product: Product, onFavoriteClick: ((Product) -> Unit)? = null) {
        ivProduct.setImageResource(product.imageRes)
        tvName.text = product.name
        tvPrice.text = "₽${String.format("%.2f", product.price)}"

        // Бейдж (Best Seller или New)
        tvBadge.visibility = if (product.isBestSeller) {
            tvBadge.text = "BEST SELLER"
            VISIBLE
        } else if (product.isNew) {
            tvBadge.text = "NEW!"
            VISIBLE
        } else {
            GONE
        }

        // Сердечко
        ivFavorite.setOnClickListener {
            onFavoriteClick?.invoke(product)
        }

        // Кнопка "В корзину"
        btnAddToCart.setOnClickListener {
            // TODO: Добавить в корзину
        }
    }
}