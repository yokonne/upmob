package com.example.upmo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val email: String,
    val password: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)

@Serializable
data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String,
    val user: User
)

@Serializable
data class SupabaseError(
    val message: String,
    val statusCode: Int? = null
)