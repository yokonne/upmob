package com.example.upmo.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upmo.R
import com.example.upmo.data.network.ProductRepository
import com.example.upmo.ui.catalog.CatalogActivity
import com.example.upmo.ui.favorites.FavoritesActivity
import com.example.upmo.ui.home.adapters.CategoryAdapter
import com.example.upmo.ui.home.adapters.ProductAdapter
import com.example.upmo.ui.home.adapters.PromotionAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var rvCategories: androidx.recyclerview.widget.RecyclerView
    private lateinit var rvPopularProducts: androidx.recyclerview.widget.RecyclerView
    private lateinit var rvPromotions: androidx.recyclerview.widget.RecyclerView
    private lateinit var bottomNav: BottomNavigationView

    private val productRepository = ProductRepository()

    private val categories = listOf("Все", "Outdoor", "Tennis", "Running", "Basketball")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupCategories()
        setupBottomNavigation()
        loadProducts()
    }

    private fun initViews() {
        rvCategories = findViewById(R.id.rvCategories)
        rvPopularProducts = findViewById(R.id.rvPopularProducts)
        rvPromotions = findViewById(R.id.rvPromotions)
        bottomNav = findViewById(R.id.bottomNav)

        rvPopularProducts.layoutManager = LinearLayoutManager(this)
        rvPromotions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupCategories() {
        val adapter = CategoryAdapter(categories)
        adapter.setOnCategoryClickListener { category ->
            lifecycleScope.launch {
                productRepository.loadProductsByCategory(category)
                updateProducts()
            }
        }
        rvCategories.adapter = adapter
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            productRepository.loadProducts()
            updateProducts()

            val newProducts = productRepository.getNewProducts()
            updatePromotions(newProducts)
        }
    }

    private fun updateProducts() {
        val products = productRepository.products.value
        val adapter = ProductAdapter(products) { product ->
            Toast.makeText(this, "Добавлено в избранное: ${product.name}", Toast.LENGTH_SHORT).show()
        }
        rvPopularProducts.adapter = adapter
    }

    private fun updatePromotions(newProducts: List<com.example.upmo.data.models.Product>) {
        val adapter = PromotionAdapter(newProducts)
        rvPromotions.adapter = adapter
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_catalog -> {
                    startActivity(Intent(this, CatalogActivity::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}