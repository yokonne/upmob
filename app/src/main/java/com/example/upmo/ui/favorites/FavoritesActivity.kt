package com.example.upmo.ui.favorites

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.upmo.R
import com.example.upmo.data.models.Product
import com.example.upmo.ui.home.adapters.ProductAdapter

class FavoritesActivity : AppCompatActivity() {
    
    private lateinit var rvFavorites: androidx.recyclerview.widget.RecyclerView
    private lateinit var tvEmpty: TextView
    
    // Временные данные для избранного
    private val favoriteProducts = listOf<Product>() // Пока пусто
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        
        rvFavorites = findViewById(R.id.rvFavorites)
        tvEmpty = findViewById(R.id.tvEmpty)
        
        rvFavorites.layoutManager = GridLayoutManager(this, 2)
        
        if (favoriteProducts.isEmpty()) {
            tvEmpty.visibility = android.view.View.VISIBLE
            rvFavorites.visibility = android.view.View.GONE
        } else {
            tvEmpty.visibility = android.view.View.GONE
            rvFavorites.visibility = android.view.View.VISIBLE
            val adapter = ProductAdapter(favoriteProducts) { product ->
                // Удаление из избранного
            }
            rvFavorites.adapter = adapter
        }
    }
}