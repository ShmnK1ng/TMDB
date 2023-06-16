package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeMovieItemBinding
import com.example.tmdb.ui.home.utils.IMAGE_BASE_PATH

class MovieItemViewHolder(private val binding: FragmentHomeMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movieDtoItem: Movie) {
        binding.fragmentHomeMovieItemTitle.text = movieDtoItem.title
        binding.fragmentHomeMovieItemRating.text = String.format("%.1f", movieDtoItem.rating)
        binding.fragmentHomeMovieItemImageView.load(IMAGE_BASE_PATH + movieDtoItem.posterPath)
    }
}