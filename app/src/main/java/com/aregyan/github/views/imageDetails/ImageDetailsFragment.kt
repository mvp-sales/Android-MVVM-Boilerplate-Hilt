package com.aregyan.github.views.imageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aregyan.github.databinding.FragmentImageDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment: Fragment() {

    private val viewModel: ImageDetailsViewModel by viewModels()
    private lateinit var binding: FragmentImageDetailsBinding
    private val args: ImageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                ImageDetailsViewModel.UiState.Initial -> {}
                is ImageDetailsViewModel.UiState.Loading -> {

                }
                is ImageDetailsViewModel.UiState.Loaded -> {
                    with(binding) {
                        pixabayImageSizeTv.text = "Size: ${uiState.imageData.imageSize}"
                        pixabayImageTypeTv.text = "Type: ${uiState.imageData.type}"
                        pixabayImageTagsTv.text = "Tags: ${uiState.imageData.tags}"

                        pixabayImageUserTv.text = uiState.imageData.username
                        pixabayImageViewsTv.text = "Views: ${uiState.imageData.views}"
                        pixabayImageLikesTv.text = "Likes: ${uiState.imageData.likes}"
                        pixabayImageCommentsTv.text = "Comments: ${uiState.imageData.comments}"
                        pixabayImageDownloadsTv.text = "Downloads: ${uiState.imageData.downloads}"

                        Glide.with(requireView())
                            .load(uiState.imageData.imageUrl)
                            .centerCrop()
                            .into(pixabayImageIv)
                    }
                }
            }
        }

        viewModel.fetchImage(args.imageId)
    }
}