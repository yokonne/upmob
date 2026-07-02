package com.example.upmo.ui.seach

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upmo.R
import com.example.upmo.data.local.SearchHistoryManager
import com.example.upmo.data.models.Product
import com.example.upmo.databinding.ActivitySearchBinding
import com.example.upmo.ui.home.adapters.ProductAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var historyAdapter: SearchHistoryAdapter
    private lateinit var resultsAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupListeners()
        observeViewModel()

        loadSearchHistory()
    }

    private fun setupRecyclerViews() {
        // Адаптер для истории поиска
        historyAdapter = SearchHistoryAdapter(
            onItemClick = { query ->
                binding.etSearch.setText(query)
                performSearch(query)
            },
            onDeleteClick = { query ->
                SearchHistoryManager.removeQuery(this, query)
                loadSearchHistory()
            }
        )

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter

        // Адаптер для результатов поиска
        resultsAdapter = ProductAdapter(emptyList()) { product ->
            // Переход на детали товара
            try {
                val intent = android.content.Intent(this, Class.forName("com.example.upmo.ui.details.DetailsActivity"))
                intent.putExtra("product_id", product.id)
                startActivity(intent)
            } catch (e: Exception) {
                AlertDialog.Builder(this)
                    .setTitle("Информация")
                    .setMessage("Детали товара будут доступны в следующем спринте")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.rvResults.layoutManager = GridLayoutManager(this, 2)
        binding.rvResults.adapter = resultsAdapter
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnClearHistory.setOnClickListener {
            SearchHistoryManager.clearHistory(this)
            loadSearchHistory()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    performSearch(query)
                }
                true
            } else false
        }
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(this) { results ->
            if (results.isNotEmpty()) {
                showResults(results)
            } else {
                val query = binding.etSearch.text.toString().trim()
                if (query.isEmpty()) {
                    showHistory()
                } else {
                    showResults(emptyList())
                }
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            if (loading) {
                binding.tvNoResults.text = "Поиск..."
                binding.tvNoResults.visibility = android.view.View.VISIBLE
            } else {
                binding.tvNoResults.visibility = android.view.View.GONE
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage(it)
                    .setPositiveButton("OK", null)
                    .setCancelable(false)
                    .show()
            }
        }
    }

    private fun performSearch(query: String) {
        // Добавляем в историю
        SearchHistoryManager.addQuery(this, query)
        loadSearchHistory()

        // Выполняем поиск
        viewModel.search(query)
    }

    private fun loadSearchHistory() {
        val history = SearchHistoryManager.getHistory(this)
        // Используем submitList через адаптер
        historyAdapter.submitList(history)
    }

    private fun showHistory() {
        binding.rvHistory.visibility = android.view.View.VISIBLE
        binding.rvResults.visibility = android.view.View.GONE
        binding.tvNoResults.visibility = android.view.View.GONE
    }

    private fun showResults(results: List<Product>) {
        binding.rvHistory.visibility = android.view.View.GONE
        binding.rvResults.visibility = android.view.View.VISIBLE

        if (results.isEmpty()) {
            binding.tvNoResults.text = "Ничего не найдено"
            binding.tvNoResults.visibility = android.view.View.VISIBLE
        } else {
            binding.tvNoResults.visibility = android.view.View.GONE
            // Используем submitList через адаптер
            resultsAdapter.submitList(results)
        }
    }
}