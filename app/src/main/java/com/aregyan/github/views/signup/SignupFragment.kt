package com.aregyan.github.views.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aregyan.github.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: Fragment() {
    private val viewModel: SignupViewModel by viewModels()
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            emailRegField.addTextChangedListener {
                viewModel.updateEmail(it.toString())
            }
            passwordRegField.addTextChangedListener {
                viewModel.updatePassword(it.toString())
            }
            ageField.addTextChangedListener {
                val age = if (it.toString().isEmpty()) -1 else it.toString().toInt()
                viewModel.updateAge(age)
            }
            signupRegButton.setOnClickListener {
                viewModel.onRegister()
            }

            viewModel.signupViewDataLiveData.observe(viewLifecycleOwner) {
                emailRegInputLayout.error = if (!it.isEmailValid && it.email.isNotEmpty()) {
                    "Please type a valid email"
                } else null
                passwordRegInputLayout.error = if (!it.isPasswordValid && it.password.isNotEmpty()) {
                    "Your password must be between 6 and 12 characters long"
                } else null
                ageRegInputLayout.error = if (!it.isAgeValid && it.age != -1) {
                    "Your age must be between 18 and 99 years old"
                } else null
                signupRegButton.isEnabled = it.isEmailValid && it.isPasswordValid && it.isAgeValid
            }
            viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
                when (viewState) {
                    SignupViewModel.ViewState.Initial -> {

                    }
                    SignupViewModel.ViewState.OnRegisterClicked -> {

                    }
                    SignupViewModel.ViewState.OnRegisterSuccessful -> {

                    }
                }
            }
        }
    }
}