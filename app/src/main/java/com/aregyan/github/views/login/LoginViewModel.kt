package com.aregyan.github.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aregyan.github.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginViewDataLiveData = MutableLiveData(LoginViewData("", ""))
    val loginViewDataLiveData: LiveData<LoginViewData> = _loginViewDataLiveData

    private val _viewStateLiveData = MutableLiveData<ViewState>(ViewState.Initial)
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData

    fun onLogin() {
        _viewStateLiveData.value = ViewState.OnLoginClicked
        _loginViewDataLiveData.value?.let {
            val successfulLogin = loginRepository.login(it.email, it.password)
            _viewStateLiveData.value = ViewState.OnLoginResult(successfulLogin)
        }
    }

    fun onRegister() {
        _viewStateLiveData.value = ViewState.OnRegisterClicked
    }

    fun updateEmail(email: String) {
        _loginViewDataLiveData.value = _loginViewDataLiveData.value?.copy(email = email)
    }

    fun updatePassword(password: String) {
        _loginViewDataLiveData.value = _loginViewDataLiveData.value?.copy(password = password)
    }

    sealed class ViewState {
        data object Initial: ViewState()
        data object OnLoginClicked: ViewState()
        data class OnLoginResult(val success: Boolean): ViewState()
        data object OnRegisterClicked: ViewState()
    }
}