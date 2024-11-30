package com.aregyan.github.repository

import com.aregyan.github.database.UsersDao
import com.aregyan.github.database.toDb
import com.aregyan.github.domain.User
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val usersDao: UsersDao
) {
    fun signUp(user: User) {
        usersDao.insertUser(user.toDb())
    }
}