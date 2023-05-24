package com.example.tmdb.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.data.Category
import com.example.tmdb.databinding.FragmentHomeItemBinding

class CategoryViewHolder(private val binding: FragmentHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val movieItemAdapter = MovieItemAdapter()

    fun bind(category: Category) {
        binding.fragmentHomeItemTextview.text = category.categoryName
        binding.fragmentHomeItemRecyclerView.adapter = movieItemAdapter
        binding.fragmentHomeItemRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        movieItemAdapter.submitList(category.movieList)
    }
}