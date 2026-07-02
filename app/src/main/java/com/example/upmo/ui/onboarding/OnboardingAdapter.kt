package com.example.upmo.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.upmo.R
import com.example.upmo.databinding.ItemOnboardingBinding

class OnboardingAdapter(private val context: android.content.Context) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    private val onboardingData = listOf(
        OnboardingItem(
            imageRes = R.drawable.one,
            title = "ДОБРО ПОЖАЛОВАТЬ",
            description = "Начнем работу\nБыстрая доставка, широкий ассортимент, с любовью к творчеству!"
        ),
        OnboardingItem(
            imageRes = R.drawable.three,
            title = "Страница 1",
            description = "Маркетолога не нанимали, но вкратце у нас круто. Эщкере сиксевен 6767"
        ),
        OnboardingItem(
            imageRes = R.drawable.miles,
            title = "Добро пожаловать!",
            description = "Начните свое путешествие с нами"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingData[position])
    }

    override fun getItemCount(): Int = onboardingData.size

    inner class OnboardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnboardingItem) {
            binding.ivOnboarding.setImageResource(item.imageRes)
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }

    data class OnboardingItem(
        val imageRes: Int,
        val title: String,
        val description: String
    )
}