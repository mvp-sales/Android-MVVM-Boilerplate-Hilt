package com.aregyan.github.views.imageDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.github.domain.PixabayImageData
import com.aregyan.github.repository.ImageDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val imageDetailsRepository: ImageDetailsRepository
): ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    fun fetchImage(id: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            imageDetailsRepository.fetchImage(id).collect {
                _uiState.postValue(UiState.Loaded(it))
            }
        }
    }

    sealed class UiState {
        data object Initial: UiState()
        data object Loading: UiState()
        data class Loaded(val imageData: PixabayImageData): UiState()
    }
}