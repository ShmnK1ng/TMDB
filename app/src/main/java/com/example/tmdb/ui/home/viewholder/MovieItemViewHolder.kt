package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeMovieItemBinding

class MovieItemViewHolder(private val binding: FragmentHomeMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind (movieItem: Movie) {
        binding.fragmentHomeMovieItemTitle.text = movieItem.title
        binding.fragmentHomeMovieItemRating.text = movieItem.rating.toString()
    }
}