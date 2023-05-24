package com.example.tmdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tmdb.data.Movie
import com.example.tmdb.databinding.FragmentHomeMovieItemBinding
import com.example.tmdb.utils.MovieItemDiffCallback

class MovieItemAdapter:
    ListAdapter<Movie, MovieItemViewHolder>(MovieItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomeMovieItemBinding.inflate(layoutInflater, parent, false)

        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.bind(movieItem)
    }
}