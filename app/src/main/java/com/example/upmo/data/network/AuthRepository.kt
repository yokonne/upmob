package com.example.upmo.data.network

class AuthRepository {
    private val authApi = SupabaseAuthApi()

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val response = authApi.login(email, password)

            true
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun signUp(name: String, email: String, password: String): Boolean {
        return try {
            val response = authApi.signUp(name, email, password)
            response.user != null
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun sendResetCode(email: String): Boolean {
        return try {
            authApi.sendResetCode(email)
            true
        } catch (e: Exception) {
            throw e
        }
    }
}
