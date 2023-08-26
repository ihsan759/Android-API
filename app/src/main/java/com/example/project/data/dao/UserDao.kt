package com.example.project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username")
    fun getByUsername(username: String): Flow<User>

    @Insert
    suspend fun insert(user: User)
}