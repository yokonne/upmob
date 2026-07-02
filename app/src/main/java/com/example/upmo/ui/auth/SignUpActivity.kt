package com.example.upmo.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.upmo.R
import com.example.upmo.ui.home.HomeActivity
import com.example.upmo.databinding.ActivitySignUpBinding
import com.example.upmo.utils.EmailValidator
import com.example.upmo.utils.PasswordValidator

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        // Кнопка назад
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Регистрация
        binding.btnRegister.setOnClickListener {
            attemptRegister()
        }

        // Переход на вход
        binding.tvSignIn.setOnClickListener {
            finish()
        }

        // Просмотр политики конфиденциальности
        binding.cbTerms.setOnClickListener {
            // Задача 6: Открыть PDF
            showPrivacyPolicyDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            binding.btnRegister.isEnabled = !loading
            binding.btnRegister.text = if (loading) "Загрузка..." else "Зарегистрироваться"
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showErrorDialog(it)
            }
        }

        viewModel.registerSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun attemptRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        // Валидация имени
        if (name.isEmpty()) {
            binding.tilName.error = "Введите ваше имя"
            return
        }

        // Валидация email
        if (!EmailValidator.isValid(email)) {
            binding.tilEmail.error = "Некорректный формат email"
            showErrorDialog("Некорректный формат email.\nИспользуйте формат name@domain.ru")
            return
        }

        // Валидация пароля
        if (!PasswordValidator.isValid(password)) {
            binding.tilPassword.error = "Пароль должен содержать минимум 6 символов"
            return
        }

        // Проверка согласия с условиями
        if (!binding.cbTerms.isChecked) {
            Toast.makeText(this, "Примите условия и политику конфиденциальности", Toast.LENGTH_SHORT).show()
            return
        }

        // Сброс ошибок
        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null

        viewModel.register(name, email, password)
    }

    private fun showPrivacyPolicyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Политика конфиденциальности")
            .setMessage("Нажмите 'ОК' для просмотра PDF документа")
            .setPositiveButton("ОК") { _, _ ->
                // Открываем PDF (задача 6)
                val pdfUrl = "https://your-server.com/privacy_policy.pdf" //!!!!!!!!!
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                startActivity(intent)
            }
            .setNegativeButton("Отмена", null)
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