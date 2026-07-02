package com.example.upmo.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upmo.R

class CategoryAdapter(
    private val categories: List<String>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0
    private var onCategoryClickListener: ((String) -> Unit)? = null

    fun setOnCategoryClickListener(listener: (String) -> Unit) {
        this.onCategoryClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position == selectedPosition)

        holder.itemView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(position)
            onCategoryClickListener?.invoke(categories[position])
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)

        fun bind(category: String, isSelected: Boolean) {
            tvCategory.text = category
            tvCategory.isSelected = isSelected
            tvCategory.setBackgroundResource(
                if (isSelected) R.drawable.bg_category_selected
                else R.drawable.bg_category
            )
            tvCategory.setTextColor(
                if (isSelected) itemView.context.getColor(R.color.white)
                else itemView.context.getColor(R.color.text_primary)
            )
        }
    }
}