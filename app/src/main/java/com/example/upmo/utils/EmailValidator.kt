package com.example.upmo.utils

import java.util.regex.Pattern

object EmailValidator {
    private val EMAIL_PATTERN = Pattern.compile(
        "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$"
    )

    fun isValid(email: String): Boolean {
        return EMAIL_PATTERN.matcher(email).matches()
    }
}