package com.example.upmo.ui.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.upmo.R
import com.example.upmo.data.network.ProductRepository
import com.example.upmo.ui.home.adapters.ProductAdapter
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class CatalogActivity : AppCompatActivity() {

    private lateinit var rvCatalog: androidx.recyclerview.widget.RecyclerView
    private val productRepository = ProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        rvCatalog = findViewById(R.id.rvCatalog)
        rvCatalog.layoutManager = GridLayoutManager(this, 2)

        loadProducts()
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            productRepository.loadProducts()
            val products = productRepository.products.value
            val adapter = ProductAdapter(products) { product ->
                // Добавление в избранное
            }
            rvCatalog.adapter = adapter
        }
    }
}