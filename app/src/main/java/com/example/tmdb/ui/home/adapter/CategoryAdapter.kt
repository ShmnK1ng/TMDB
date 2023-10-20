package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tmdb.data.model.Category
import com.example.tmdb.databinding.FragmentHomeItemBinding
import com.example.tmdb.ui.home.viewholder.CategoryViewHolder
import com.example.tmdb.ui.home.utils.CategoryDiffCallback

class CategoryAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomeItemBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}