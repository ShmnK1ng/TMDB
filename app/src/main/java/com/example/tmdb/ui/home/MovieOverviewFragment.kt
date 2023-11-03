package com.example.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.tmdb.data.model.FRAGMENT_MOVIE_OVERVIEW_SPAN_COUNT
import com.example.tmdb.data.model.toStringDate
import com.example.tmdb.databinding.FragmentMovieItemOverviewBinding
import com.example.tmdb.ui.home.adapter.GenreAdapter
import com.example.tmdb.ui.home.utils.flowWithStartedLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MovieOverviewFragment : Fragment() {

    private var binding: FragmentMovieItemOverviewBinding? = null
    @Inject
    lateinit var factory: MovieOverviewViewModel.Factory
    private val args: MovieOverviewFragmentArgs by navArgs()
    private val viewModel: MovieOverviewViewModel by viewModels {
        MovieOverviewViewModel.provideMovieOverviewViewModelFactory(factory, args.argId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieItemOverviewBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genreAdapter = GenreAdapter()
        binding?.let { binding ->
            binding.fragmentMovieItemOverviewRecyclerview.adapter = genreAdapter
            binding.fragmentMovieItemOverviewRecyclerview.layoutManager = StaggeredGridLayoutManager(
                FRAGMENT_MOVIE_OVERVIEW_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL)
        }
        viewModel.movieOverview.flowWithStartedLifecycle(viewLifecycleOwner)
            .filterNotNull()
            .onEach {
                binding?.let {binding ->
                    binding.fragmentMovieItemOverviewPoster.load(it.backdropPath)
                    binding.fragmentMovieItemOverviewTitle.text = it.title
                    binding.fragmentMovieItemOverviewRatingTextview.text = it.rating.toString()
                    binding.fragmentMovieItemOverviewReleaseDateTextView.text = it.releaseDate.toStringDate(context)
                    binding.fragmentMovieItemOverviewOverviewText.text = it.overview
                }

                genreAdapter.submitList(it.genres)
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}