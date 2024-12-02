package com.aregyan.github.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aregyan.github.databinding.FragmentHomeBinding
import com.gk.emon.lovelyLoading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment() {

    @Inject
    lateinit var adapter: ImageListAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            adapter.clickListener.onItemClick = {
                findNavController().navigate(HomeFragmentDirections.actionHomeToImageDetails(it.id))
            }

            viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    HomeViewModel.UiState.Initial -> {}
                    HomeViewModel.UiState.Loading ->
                        LoadingPopup.showLoadingPopUp()
                    is HomeViewModel.UiState.Loaded -> {
                        LoadingPopup.hideLoadingPopUp()
                        adapter.submitList(uiState.imageDataList)
                    }
                }
            }
        }

        viewModel.fetchImages()
    }
}