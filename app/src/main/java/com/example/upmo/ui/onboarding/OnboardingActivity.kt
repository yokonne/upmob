package com.example.upmo.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.upmo.R
import com.example.upmo.databinding.ActivityOnboardingBinding
import com.example.upmo.ui.auth.MainActivity
import com.example.upmo.utils.SharedPrefsManager

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupDotIndicators()
        setupListeners()
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(this)
        binding.viewPager.adapter = adapter

        // Анимация переключения
        binding.viewPager.setPageTransformer { page, position ->
            page.alpha = 1 - Math.abs(position)
            page.translationX = -position * page.width * 0.3f
        }
    }

    private fun setupDotIndicators() {
        val dotCount = adapter.itemCount
        val dots = arrayOfNulls<android.view.View>(dotCount)

        for (i in 0 until dotCount) {
            dots[i] = android.view.View(this).apply {
                setBackgroundResource(
                    if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive
                )
                val params = android.widget.LinearLayout.LayoutParams(
                    24, 24
                ).apply {
                    marginStart = 8
                    marginEnd = 8
                }
                layoutParams = params
            }
            binding.dotsContainer.addView(dots[i])
        }

        // Обновляем индикаторы при смене страницы
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Обновляем точки
                for (i in 0 until dotCount) {
                    dots[i]?.setBackgroundResource(
                        if (i == position) R.drawable.dot_active else R.drawable.dot_inactive
                    )
                }

                // Меняем текст на кнопке (Задача 6)
                if (position == adapter.itemCount - 1) {
                    binding.btnNext.text = "Начать"
                } else {
                    binding.btnNext.text = "Далее"
                }
            }
        })
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                // Последний слайд - переходим на экран входа
                SharedPrefsManager.setFirstLaunch(this, false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}