package com.example.upmo.data.network

import com.example.upmo.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductRepository {

    private val productApi = ProductApi()

    // Кэш товаров
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Загрузка всех товаров
     */
    suspend fun loadProducts() {
        _isLoading.value = true
        _error.value = null

        try {
            val products = productApi.getProducts()
            _products.value = products
        } catch (e: Exception) {
            _error.value = e.message ?: "Ошибка загрузки товаров"
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Загрузка товаров по категории
     */
    suspend fun loadProductsByCategory(category: String) {
        _isLoading.value = true
        _error.value = null

        try {
            val products = if (category == "Все") {
                productApi.getProducts()
            } else {
                productApi.getProductsByCategory(category)
            }
            _products.value = products
        } catch (e: Exception) {
            _error.value = e.message ?: "Ошибка загрузки товаров"
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Получение популярных товаров
     */
    suspend fun getBestSellers(): List<Product> {
        return try {
            productApi.getBestSellers()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Получение новых товаров
     */
    suspend fun getNewProducts(): List<Product> {
        return try {
            productApi.getNewProducts()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Получение товара по ID
     */
    suspend fun getProductById(productId: String): Product? {
        return try {
            productApi.getProductById(productId)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Очистка ошибки
     */
    fun clearError() {
        _error.value = null
    }
}