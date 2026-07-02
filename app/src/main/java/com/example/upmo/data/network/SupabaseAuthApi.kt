package com.example.upmo.data.network

import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class SupabaseAuthApi {

    private val client = SupabaseManager.client.httpClient

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class LoginRequest(
        val email: String,
        val password: String
    )

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class AuthResponse(
        val access_token: String,
        val user: UserData
    )

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class UserData(
        val id: String,
        val email: String
    )

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class SignUpRequest(
        val email: String,
        val password: String,
        val data: UserMetadata? = null
    )

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class UserMetadata(
        val first_name: String
    )

    @Serializable  // <-- ДОЛЖНО БЫТЬ
    data class ResetPasswordRequest(
        val email: String
    )

    suspend fun login(email: String, password: String): AuthResponse {
        return try {
            val response = client.post("${Constants.SUPABASE_URL}/auth/v1/token?grant_type=password") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                    append("Authorization", "Bearer ${Constants.SUPABASE_KEY}")
                }
                setBody(LoginRequest(email, password))
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                throw Exception("Ошибка авторизации")
            }
        } catch (e: Exception) {
            throw Exception("Ошибка соединения: ${e.message}")
        }
    }

// В классе AuthApi добавьте:

    suspend fun signUp(name: String, email: String, password: String): AuthResponse {
        return try {
            val response = client.post("${Constants.SUPABASE_URL}/auth/v1/signup") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                }
                setBody(SignUpRequest(
                    email = email,
                    password = password,
                    data = UserMetadata(first_name = name)
                ))
            }

            if (response.status.value in 200..299) {
                response.body()
            } else {
                throw Exception("Ошибка регистрации")
            }
        } catch (e: Exception) {
            throw Exception("Ошибка: ${e.message}")
        }
    }

    suspend fun sendResetCode(email: String) {
        try {
            val response = client.post("${Constants.SUPABASE_URL}/auth/v1/recover") {
                contentType(ContentType.Application.Json)
                headers {
                    append("apikey", Constants.SUPABASE_KEY)
                }
                setBody(ResetPasswordRequest(email))
            }

            if (response.status.value !in 200..299) {
                throw Exception("Ошибка отправки кода")
            }
        } catch (e: Exception) {
            throw Exception("Ошибка: ${e.message}")
        }
    }
}