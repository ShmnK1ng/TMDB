package com.example.tmdb.ui.home.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.tmdb.data.model.Genre

class GenreDiffCallback: DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}