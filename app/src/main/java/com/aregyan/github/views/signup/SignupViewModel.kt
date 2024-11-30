package com.aregyan.github.views.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aregyan.github.domain.User
import com.aregyan.github.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
): ViewModel() {

    private val _signupViewDataLiveData = MutableLiveData(SignupViewData("", "", age = -1))
    val signupViewDataLiveData: LiveData<SignupViewData> = _signupViewDataLiveData

    private val _viewStateLiveData = MutableLiveData<ViewState>(ViewState.Initial)
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData

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
        _viewStateLiveData.value = ViewState.OnRegisterSuccessful
        _signupViewDataLiveData.value?.let {
            signupRepository.signUp(User(it.email, it.password, it.age))
            _viewStateLiveData.value = ViewState.OnRegisterSuccessful
        }
    }

    sealed class ViewState {
        data object Initial: ViewState()
        data object OnRegisterClicked: ViewState()
        data object OnRegisterSuccessful: ViewState()
    }
}