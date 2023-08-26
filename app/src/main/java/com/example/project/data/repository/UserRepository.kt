package com.example.project.data.repository

import com.example.project.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserByUsername(username: String): Flow<User>

    suspend fun insertUser(user: User)
}