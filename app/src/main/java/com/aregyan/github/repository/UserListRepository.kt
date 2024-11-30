package com.aregyan.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.aregyan.github.database.Database
import com.aregyan.github.database.asDomainModel
import com.aregyan.github.domain.UserListItem
import com.aregyan.github.network.UserListService
import com.aregyan.github.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListService: UserListService,
    private val database: Database
) {

    val users: LiveData<List<UserListItem>?> = database.usersDao.getDatabaseUsers().map { it?.asDomainModel() }

    suspend fun refreshUserList() {
        try {
            val users = userListService.getUserList()
            database.usersDao.insertAll(users.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}