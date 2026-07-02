package com.example.upmo.ui.popular

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.upmo.R
import com.example.upmo.data.network.ProductRepository
import com.example.upmo.ui.favorites.FavoritesActivity
import com.example.upmo.ui.home.adapters.ProductAdapter
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class PopularActivity : AppCompatActivity() {
    
    private lateinit var rvPopular: androidx.recyclerview.widget.RecyclerView
    private val productRepository = ProductRepository()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular)
        
        rvPopular = findViewById(R.id.rvPopular)
        rvPopular.layoutManager = GridLayoutManager(this, 2)
        
        loadPopularProducts()
    }
    
    private fun loadPopularProducts() {
        lifecycleScope.launch {
            val products = productRepository.getBestSellers()
            val adapter = ProductAdapter(products) { product ->
                // Переход в Избранное (Задача 12)
                startActivity(Intent(this@PopularActivity, FavoritesActivity::class.java))
            }
            rvPopular.adapter = adapter
        }
    }
}