package com.example.upmo.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,  // UUID из Supabase
    val name: String,
    val price: Double,
    val description: String? = null,
    val image_url: String? = null,
    val category: String? = null,
    val is_best_seller: Boolean = false,
    val is_new: Boolean = false,
    val rating: Float = 4.5f,
    val created_at: String? = null,
    val updated_at: String? = null
) {
    // Для совместимости с существующим кодом
    val imageRes: Int
        get() = 0 // Временное решение, потом будем загружать из URL

    val isBestSeller: Boolean
        get() = is_best_seller

    val isNew: Boolean
        get() = is_new
}