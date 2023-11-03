package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.model.Genre
import com.example.tmdb.databinding.FragmentMovieOverviewGanreBinding

class GenreViewHolder(
    private val binding: FragmentMovieOverviewGanreBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: Genre) {
        binding.fragmentMovieOverviewGenreTextView.text = genre.name
    }
}