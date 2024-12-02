package com.aregyan.github.views.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.github.domain.User
import com.aregyan.github.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
): ViewModel() {

    private val _signupViewDataLiveData = MutableLiveData(SignupViewData("", "", age = -1))
    val signupViewDataLiveData: LiveData<SignupViewData> = _signupViewDataLiveData

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    fun updateEmail(email: String) {
        _signupViewDataLiveData.value = _signupViewDataLiveData.value?.copy(email = email)
    }

    fun updatePassword(password: String) {
        _signupViewDataLiveData.value = _signupViewDataLiveData.value?.copy(password = password)
    }

    fun updateAge(age: Int) {
        if (age != -1) {
            _signupViewDataLiveData.value = _signupViewDataLiveData.value?.copy(age = age)
        }
    }

    fun onRegister() {
        _uiState.value = UiState.OnRegisterSuccessful
        _signupViewDataLiveData.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                signupRepository.signUp(User(it.email, it.password, it.age)).collect {
                    _uiState.postValue(UiState.OnRegisterSuccessful)
                }
            }
        }
    }

    sealed class UiState {
        data object Initial: UiState()
        data object OnRegisterClicked: UiState()
        data object OnRegisterSuccessful: UiState()
    }
}