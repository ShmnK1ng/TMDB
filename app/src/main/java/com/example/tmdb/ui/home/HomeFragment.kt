package com.example.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.data.model.FRAGMENT_HOME_SPAN_COUNT
import com.example.tmdb.data.model.Movie
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.network.Result
import com.example.tmdb.ui.home.adapter.CategoryAdapter
import com.example.tmdb.ui.home.adapter.OnItemClickListener
import com.example.tmdb.ui.home.utils.flowWithStartedLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(movieItem: Movie) {
                viewModel.onMovieItemClicked(movieItem)
            }
        }
        setupErrorsObserver()
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
        binding?.let { binding ->
            binding.fragmentHomeRecyclerView.adapter = categoryAdapter
            binding.fragmentHomeRecyclerView.layoutManager = StaggeredGridLayoutManager(FRAGMENT_HOME_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        }
        viewModel.categories.flowWithStartedLifecycle(viewLifecycleOwner)
            .onEach { categoryAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showToast(error: Result.Failure) {
        when (error) {
            is Result.Failure.NetworkError -> Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.error_connection_message),
                Toast.LENGTH_SHORT
            ).show()

            is Result.Failure.OtherError -> Toast.makeText(requireContext(), requireContext().getString(R.string.error_other_message), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupErrorsObserver() {
        viewModel.showError.flowWithStartedLifecycle(viewLifecycleOwner)
            .onEach { error ->
                if (error != null) {
                    showToast(error)
                    viewModel.resetErrorState()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}