package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.FRAGMENT_HOME_ITEM_SPAN_COUNT
import com.example.tmdb.databinding.FragmentHomeItemBinding
import com.example.tmdb.ui.home.adapter.MovieItemAdapter

class CategoryViewHolder(
    private val binding: FragmentHomeItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val movieItemAdapter = MovieItemAdapter()

    init {
        binding.fragmentHomeItemRecyclerView.adapter = movieItemAdapter
        binding.fragmentHomeItemRecyclerView.layoutManager =
            StaggeredGridLayoutManager(FRAGMENT_HOME_ITEM_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL)
    }

    fun bind(category: Category) {
        binding.fragmentHomeItemTextview.text = binding.fragmentHomeItemTextview.context.getString(category.categoryNameId)
        movieItemAdapter.submitList(category.movieList)
    }
}