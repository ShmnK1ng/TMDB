package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.FRAGMENT_HOME_ITEM_SPAN_COUNT
import com.example.tmdb.databinding.FragmentHomeItemBinding
import com.example.tmdb.ui.home.adapter.MovieItemAdapter
import com.example.tmdb.ui.home.adapter.OnItemClickListener

class CategoryViewHolder(
    private val binding: FragmentHomeItemBinding,
    onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val movieItemAdapter = MovieItemAdapter(onItemClickListener)

    init {
        binding.fragmentHomeItemRecyclerView.adapter = movieItemAdapter
        binding.fragmentHomeItemRecyclerView.layoutManager =
            StaggeredGridLayoutManager(FRAGMENT_HOME_ITEM_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL)
    }

    fun bind(category: Category) {
        binding.fragmentHomeItemTextview.text = binding.fragmentHomeItemTextview.context.getString(category.categoryName.name)
        movieItemAdapter.submitList(category.movieList)
    }
}