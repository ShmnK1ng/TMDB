package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tmdb.data.model.Genre
import com.example.tmdb.databinding.FragmentMovieOverviewGanreBinding
import com.example.tmdb.ui.home.utils.GenreDiffCallback
import com.example.tmdb.ui.home.viewholder.GenreViewHolder

class GenreAdapter: ListAdapter<Genre, GenreViewHolder>(GenreDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentMovieOverviewGanreBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }
}