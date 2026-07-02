package com.example.upmo.utils

object PasswordValidator {
    fun isValid(password: String): Boolean {
        return password.length >= 6
    }
}