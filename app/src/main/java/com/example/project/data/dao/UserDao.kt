package com.example.project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Insert
    suspend fun insert(user: User)
}