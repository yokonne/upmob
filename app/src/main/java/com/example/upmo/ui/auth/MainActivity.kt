package com.example.upmo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.upmo.R
import com.example.upmo.ui.home.HomeActivity  // <-- ДОБАВЬТЕ ЭТОТ ИМПОРТ

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvCreateAccount: TextView

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
        observeViewModel()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvCreateAccount = findViewById(R.id.tvCreateAccount)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            attemptLogin()
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            btnLogin.isEnabled = !loading
            btnLogin.text = if (loading) "Загрузка..." else "Войти"
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showErrorDialog(it)
            }
        }

        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Успешный вход!", Toast.LENGTH_LONG).show()
                // ПЕРЕХОД НА HOME ACTIVITY
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // Закрываем MainActivity чтобы нельзя было вернуться назад
            }
        }
    }

    private fun attemptLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (!viewModel.validateEmail(email)) {
            etEmail.error = viewModel.errorMessage.value
            return
        }

        if (!viewModel.validatePassword(password)) {
            etPassword.error = viewModel.errorMessage.value
            return
        }

        etEmail.error = null
        etPassword.error = null

        viewModel.login(email, password)
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