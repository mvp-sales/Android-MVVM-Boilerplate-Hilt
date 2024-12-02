package com.aregyan.github.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbUser::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract val usersDao: UsersDao
}