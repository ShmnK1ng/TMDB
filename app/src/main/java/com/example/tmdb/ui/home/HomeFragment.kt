package com.example.tmdb.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.data.model.FRAGMENT_HOME_SPAN_COUNT
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.home.adapter.CategoryAdapter
import com.example.tmdb.ui.home.adapter.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(movieItem: Movie) {
                viewModel.onMovieItemClicked(movieItem)
            }
        }
        viewModel.goToMovieOverview.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            if (it != null) {
                val args = Bundle().apply {
                    putSerializable("movieItem", it)
                }
                controller.navigate(R.id.movieOverviewFragment, args)
                viewModel.resetClickState()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        val categoryAdapter = CategoryAdapter(onItemClickListener)
        binding.fragmentHomeRecyclerView.adapter = categoryAdapter
        binding.fragmentHomeRecyclerView.layoutManager =
            StaggeredGridLayoutManager(
                FRAGMENT_HOME_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL
            )
        viewModel.categories.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .onEach { categoryAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}