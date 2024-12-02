package com.aregyan.github.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.github.repository.HomeRepository
import com.aregyan.github.views.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState
    private var currentPage: Int = 1

    fun fetchImages() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchImages(currentPage).collect { imagesData ->
                _uiState.postValue(
                    UiState.Loaded(
                        imagesData.map {
                            it.toViewData()
                        }
                    )
                )
            }
        }
    }

    fun fetchNextPage() {
        currentPage++
        fetchImages()
    }

    fun fetchPreviousPage() {
        if (currentPage > 1) {
            currentPage--
            fetchImages()
        }
    }

    fun resetState() {
        _uiState.value = UiState.Initial
    }

    sealed class UiState {
        data object Initial: UiState()
        data object Loading: UiState()
        data class Loaded(val imageDataList: List<ImageViewData>): UiState()
    }
}