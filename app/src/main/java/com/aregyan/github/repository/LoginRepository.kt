package com.aregyan.github.repository

import com.aregyan.github.database.UsersDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val usersDao: UsersDao
) {

    fun login(email: String, password: String): Flow<Boolean> =
        usersDao.getUserByEmail(email).map { user ->
            delay(3000)
            user?.let {
                return@map it.password == password
            } ?: false
        }
}