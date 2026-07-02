package com.example.upmo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.upmo.R
import com.example.upmo.ui.auth.MainActivity
import com.example.upmo.ui.onboarding.OnboardingActivity
import com.example.upmo.utils.SharedPrefsManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // Проверяем, был ли уже показан Onboarding
            val isFirstLaunch = SharedPrefsManager.isFirstLaunch(this)

            val intent = if (isFirstLaunch) {
                Intent(this, OnboardingActivity::class.java)
            } else {
                // TODO: Проверить авторизацию
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000) // 2 секунды
    }
}