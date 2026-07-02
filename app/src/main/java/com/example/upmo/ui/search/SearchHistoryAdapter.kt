package com.example.upmo.ui.seach

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.upmo.R
import com.example.upmo.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter(
    private val onItemClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.HistoryViewHolder>() {
    
    private var items: List<String> = emptyList()
    
    fun submitList(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding, onItemClick, onDeleteClick)
    }
    
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount(): Int = items.size
    
    class HistoryViewHolder(
        private val binding: ItemSearchHistoryBinding,
        private val onItemClick: (String) -> Unit,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private var currentQuery: String? = null
        
        init {
            binding.root.setOnClickListener {
                currentQuery?.let { onItemClick(it) }
            }
            
            binding.btnDelete.setOnClickListener {
                currentQuery?.let { onDeleteClick(it) }
            }
        }
        
        fun bind(query: String) {
            currentQuery = query
            binding.tvQuery.text = query
        }
    }
}