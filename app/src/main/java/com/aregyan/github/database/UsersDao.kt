package com.aregyan.github.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    // user List
    @Query("select * from DbUser")
    fun getUsers(): Flow<List<DbUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<DbUser>)

    // single user
    @Query("select * from DbUser WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): Flow<DbUser?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DbUser)
}

