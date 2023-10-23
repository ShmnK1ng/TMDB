package com.example.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.tmdb.data.model.FRAGMENT_MOVIE_OVERVIEW_SPAN_COUNT
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentMovieItemOverviewBinding
import com.example.tmdb.ui.home.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieOverviewFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemOverviewBinding
    private val viewModel: MovieOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieItemOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        if (args != null) {
                viewModel.viewCreated(args.getSerializable("movieItem") as Movie)
        }
        val genreAdapter = GenreAdapter()
        binding.fragmentMovieItemOverviewRecyclerview.adapter = genreAdapter
        binding.fragmentMovieItemOverviewRecyclerview.layoutManager =  StaggeredGridLayoutManager(
            FRAGMENT_MOVIE_OVERVIEW_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL)
        viewModel.movieOverview.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            if (it != null) {
                binding.fragmentMovieItemOverviewPoster.load(it.backdropPath)
                binding.fragmentMovieItemOverviewTitle.text = it.title
                binding.fragmentMovieItemOverviewRatingTextview.text = it.rating.toString()
                binding.fragmentMovieItemOverviewReleaseDateTextView.text = it.releaseDate
                binding.fragmentMovieItemOverviewOverviewText.text = it.overview
                genreAdapter.submitList(it.genres)
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}