package com.aregyan.github.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aregyan.github.domain.User

@Entity
data class DbUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password: String,
    val age: Int
)

fun DbUser.toDomain() = User(email, password, age)
fun User.toDb() = DbUser(email = email, password = password, age = age)
