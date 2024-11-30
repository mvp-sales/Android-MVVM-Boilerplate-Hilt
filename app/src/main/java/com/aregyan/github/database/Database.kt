package com.aregyan.github.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseUserListItem::class, DatabaseUserDetails::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract val usersDao: UsersDao
}