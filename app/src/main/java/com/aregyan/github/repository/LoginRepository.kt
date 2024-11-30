package com.aregyan.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.aregyan.github.database.UsersDao
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val usersDao: UsersDao
) {

    fun login(email: String, password: String): Boolean =
        usersDao.getUserByEmail(email)?.let { user ->
            return@let user.password == password
        } ?: false
}