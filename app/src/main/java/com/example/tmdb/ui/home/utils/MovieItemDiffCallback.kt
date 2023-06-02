package com.example.tmdb.ui.home.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.tmdb.data.model.Movie

class MovieItemDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}