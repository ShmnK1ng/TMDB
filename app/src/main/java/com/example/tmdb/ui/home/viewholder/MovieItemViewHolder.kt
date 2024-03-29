package com.example.tmdb.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeMovieItemBinding
import com.example.tmdb.data.model.ROUNDING_PARAMETER
import com.example.tmdb.ui.home.adapter.OnItemClickListener

class MovieItemViewHolder(
    private val binding: FragmentHomeMovieItemBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieItem: Movie) {
        binding.fragmentHomeMovieItemTitle.text = movieItem.title
        binding.fragmentHomeMovieItemRating.text =
            String.format(ROUNDING_PARAMETER, movieItem.rating)
        binding.fragmentHomeMovieItemImageView.load(movieItem.posterPath)
        binding.fragmentHomeMovieItemImageView.setOnClickListener {
            onItemClickListener.onClick(movieItem)
        }
    }
}