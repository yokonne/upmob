package com.example.upmo.data.network

import com.example.upmo.data.models.Product
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class ProductApi {

    private val client = SupabaseManager.client.httpClient

    /**
     * Получение всех товаров
     */
    suspend fun getProducts(): List<Product> {
        return try {
            val response = client.get("${Constants.SUPABASE_URL}/rest/v1/products") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Получение товаров по категории
     */
    suspend fun getProductsByCategory(category: String): List<Product> {
        return try {
            val response = client.get("${Constants.SUPABASE_URL}/rest/v1/products") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
                parameter("category", "eq.$category")
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Получение товара по ID
     */
    suspend fun getProductById(productId: String): Product? {
        return try {
            val response = client.get("${Constants.SUPABASE_URL}/rest/v1/products") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
                parameter("id", "eq.$productId")
            }

            if (response.status.value in 200..299) {
                val products = response.body<List<Product>>()
                products.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Получение популярных товаров (Best Sellers)
     */
    suspend fun getBestSellers(): List<Product> {
        return try {
            val response = client.get("${Constants.SUPABASE_URL}/rest/v1/products") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
                parameter("is_best_seller", "eq.true")
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Получение новых товаров
     */
    suspend fun getNewProducts(): List<Product> {
        return try {
            val response = client.get("${Constants.SUPABASE_URL}/rest/v1/products") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
                parameter("is_new", "eq.true")
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}