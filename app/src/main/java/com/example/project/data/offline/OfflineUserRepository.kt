package com.example.project.data.offline

import com.example.project.data.dao.UserDao
import com.example.project.data.model.User
import com.example.project.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getUserByUsername(username: String): Flow<User> = userDao.getByUsername(username)
    override suspend fun insertUser(user: User) = userDao.insert(user)
}