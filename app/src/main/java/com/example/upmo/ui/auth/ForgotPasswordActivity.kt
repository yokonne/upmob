package com.example.upmo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.upmo.R
import com.example.upmo.databinding.ActivityForgotPasswordBinding
import com.example.upmo.utils.EmailValidator

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (!EmailValidator.isValid(email)) {
                binding.tilEmail.error = "Некорректный формат email"
                showErrorDialog("Некорректный формат email.\nИспользуйте формат name@domain.ru")
                return@setOnClickListener
            }

            binding.tilEmail.error = null
            viewModel.sendResetCode(email)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            binding.btnSend.isEnabled = !loading
            binding.btnSend.text = if (loading) "Отправка..." else "Отправить"
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showErrorDialog(it)
            }
        }

        viewModel.codeSent.observe(this) { sent ->
            if (sent) {
                showSuccessDialog()
            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Успешно")
            .setMessage("Код подтверждения отправлен на ваш email")
            .setPositiveButton("OK") { _, _ ->
                // Переход на OTP Verification
                startActivity(Intent(this, OTPVerificationActivity::class.java))
            }
            .setCancelable(false)
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show()
    }
}