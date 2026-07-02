package com.example.upmo.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upmo.data.network.AuthRepository
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _codeSent = MutableLiveData(false)
    val codeSent: LiveData<Boolean> = _codeSent

    fun sendResetCode(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val success = repository.sendResetCode(email)
                _codeSent.value = success
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка отправки кода"
            } finally {
                _isLoading.value = false
            }
        }
    }
}