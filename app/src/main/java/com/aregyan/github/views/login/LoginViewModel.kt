package com.aregyan.github.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.github.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginViewDataLiveData = MutableLiveData(LoginViewData("", ""))
    val loginViewDataLiveData: LiveData<LoginViewData> = _loginViewDataLiveData

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    fun onLogin() {
        _uiState.value = UiState.OnLoginClicked
        _loginViewDataLiveData.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                loginRepository.login(it.email, it.password).collect { loginResult ->
                    _uiState.postValue(UiState.OnLoginResult(loginResult))
                }
            }
        }
    }

    fun onRegister() {
        _uiState.value = UiState.OnRegisterClicked
    }

    fun updateEmail(email: String) {
        _loginViewDataLiveData.value = _loginViewDataLiveData.value?.copy(email = email)
    }

    fun updatePassword(password: String) {
        _loginViewDataLiveData.value = _loginViewDataLiveData.value?.copy(password = password)
    }

    sealed class UiState {
        data object Initial: UiState()
        data object OnLoginClicked: UiState()
        data class OnLoginResult(val success: Boolean): UiState()
        data object OnRegisterClicked: UiState()
    }
}