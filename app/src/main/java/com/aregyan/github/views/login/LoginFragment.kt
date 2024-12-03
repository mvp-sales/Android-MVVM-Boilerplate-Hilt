package com.aregyan.github.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aregyan.github.databinding.FragmentLoginBinding
import com.gk.emon.lovelyLoading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoadingPopup.getInstance(requireActivity())
            .defaultLovelyLoading()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            emailField.addTextChangedListener {
                viewModel.updateEmail(it.toString())
            }
            passwordField.addTextChangedListener {
                viewModel.updatePassword(it.toString())
            }
            loginButton.setOnClickListener {
                viewModel.onLogin()
            }
            signUpButton.setOnClickListener {
                viewModel.onRegister()
            }

            viewModel.loginViewDataLiveData.observe(viewLifecycleOwner) {
                emailFieldInputLayout.error = if (!it.isEmailValid && it.email.isNotEmpty()) {
                    "Please type a valid email"
                } else null
                passwordFieldInputLayout.error = if (!it.isPasswordValid && it.password.isNotEmpty()) {
                    "Your password must be between 6 and 12 characters long"
                } else null
                loginButton.isEnabled = it.isEmailValid && it.isPasswordValid
            }
            viewModel.uiState.observe(viewLifecycleOwner) { viewState ->
                when (viewState) {
                    LoginViewModel.UiState.Initial -> {

                    }
                    LoginViewModel.UiState.OnLoginClicked -> {
                        LoadingPopup.showLoadingPopUp()
                    }
                    is LoginViewModel.UiState.OnLoginResult -> {
                        LoadingPopup.hideLoadingPopUp()
                        if (viewState.success) {
                            findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                        } else {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Authentication failed")
                                .setMessage("Wrong credentials")
                                .setPositiveButton("Ok") { _, _ -> }
                                .show()
                        }
                    }
                    LoginViewModel.UiState.OnRegisterClicked -> {
                        findNavController().navigate(LoginFragmentDirections.actionLoginToSignup())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetState()
    }
}