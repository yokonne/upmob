package com.example.upmo.ui.seach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upmo.data.models.Product
import com.example.upmo.data.network.ProductRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    
    private val productRepository = ProductRepository()
    
    private val _searchResults = MutableLiveData<List<Product>>(emptyList())
    val searchResults: LiveData<List<Product>> = _searchResults
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    fun search(query: String) {
        if (query.isEmpty()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                // Поиск на сервере (нечувствительный к регистру)
                val allProducts = productRepository.products.value
                val results = allProducts.filter { product ->
                    product.name.contains(query, ignoreCase = true) ||
                    product.description?.contains(query, ignoreCase = true) == true ||
                    product.category?.contains(query, ignoreCase = true) == true
                }
                _searchResults.value = results
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка поиска"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}