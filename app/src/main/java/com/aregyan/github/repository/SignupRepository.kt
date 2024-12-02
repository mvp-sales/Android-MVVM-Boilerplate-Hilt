package com.aregyan.github.repository

import com.aregyan.github.database.UsersDao
import com.aregyan.github.database.toDb
import com.aregyan.github.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val usersDao: UsersDao
) {
    fun signUp(user: User): Flow<Unit> = flow {
        usersDao.insertUser(user.toDb())
        delay(3000)
    }
}