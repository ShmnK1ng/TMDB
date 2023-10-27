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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.data.model.FRAGMENT_HOME_SPAN_COUNT
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.home.adapter.CategoryAdapter
import com.example.tmdb.ui.home.adapter.OnItemClickListener
import com.example.tmdb.ui.home.utils.flowWithStartedLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(movieItem: Movie) {
                viewModel.onMovieItemClicked(movieItem)
            }
        }
        setupMovieOverviewNavigation(controller)
        setupCategoryAdapter(onItemClickListener)
    }

    private fun setupMovieOverviewNavigation(controller: NavController) {
        viewModel.goToMovieOverview.flowWithStartedLifecycle(viewLifecycleOwner)
            .filterNotNull()
            .onEach { movieItem ->
                controller.navigate(HomeFragmentDirections.actionHomeFragmentToMovieOverviewFragment(movieItem))
                viewModel.resetClickState()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupCategoryAdapter(onItemClickListener: OnItemClickListener) {
        val categoryAdapter = CategoryAdapter(onItemClickListener)
        binding.fragmentHomeRecyclerView.adapter = categoryAdapter
        binding.fragmentHomeRecyclerView.layoutManager = StaggeredGridLayoutManager(
            FRAGMENT_HOME_SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        viewModel.categories.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { categoryAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}