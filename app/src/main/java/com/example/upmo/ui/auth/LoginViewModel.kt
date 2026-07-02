package com.example.upmo.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upmo.data.network.AuthRepository
import com.example.upmo.utils.EmailValidator
import com.example.upmo.utils.PasswordValidator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    init {
        _isLoading.value = false
        _errorMessage.value = null
        _loginSuccess.value = false
    }

    fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                _errorMessage.value = "Email не может быть пустым"
                false
            }
            !EmailValidator.isValid(email) -> {
                _errorMessage.value = "Некорректный формат email"
                false
            }
            else -> {
                _errorMessage.value = null
                true
            }
        }
    }

    fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                _errorMessage.value = "Пароль не может быть пустым"
                false
            }
            !PasswordValidator.isValid(password) -> {
                _errorMessage.value = "Пароль должен содержать минимум 6 символов"
                false
            }
            else -> {
                _errorMessage.value = null
                true
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val success = repository.login(email, password)
                _loginSuccess.value = success
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка входа"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _errorMessage.value = null
        _loginSuccess.value = false
    }
}