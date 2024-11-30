package com.aregyan.github.views.signup

import android.util.Patterns


private const val PASSWORD_MIN_LENGTH = 6
private const val PASSWORD_MAX_LENGTH = 12
private const val AGE_MIN = 18
private const val AGE_MAX = 99
data class SignupViewData(
    val email: String,
    val password: String,
    val age: Int
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH
    val isAgeValid = age in AGE_MIN..AGE_MAX
}
