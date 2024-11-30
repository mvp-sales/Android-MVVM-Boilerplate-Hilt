package com.aregyan.github.views.login

import android.util.Patterns

private const val PASSWORD_MIN_LENGTH = 6
private const val PASSWORD_MAX_LENGTH = 12

data class LoginViewData(
    val email: String,
    val password: String
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH
}