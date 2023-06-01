package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.data.model.Category
import com.example.tmdb.databinding.FragmentHomeItemBinding
import com.example.tmdb.ui.home.adapter.MovieItemAdapter

class CategoryViewHolder(private val binding: FragmentHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val movieItemAdapter = MovieItemAdapter()

    init {
        binding.fragmentHomeItemRecyclerView.adapter = movieItemAdapter
        binding.fragmentHomeItemRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
    }

    fun bind(category: Category) {
        binding.fragmentHomeItemTextview.text = category.categoryName
        movieItemAdapter.submitList(category.movieList)
    }
}