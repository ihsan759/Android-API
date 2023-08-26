package com.example.project.data

import android.content.Context
import com.example.project.data.database.NobarDatabase
import com.example.project.data.offline.OfflineUserRepository
import com.example.project.data.offline.OfflineWhiteListRepository
import com.example.project.data.repository.UserRepository
import com.example.project.data.repository.WhiteListRepository

interface AppContainer {
    val userRepository : UserRepository
    val whiteListRepository : WhiteListRepository
}

class  AppDataContainer(private val context: Context) : AppContainer{
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(NobarDatabase.getDatabase(context).userDao())
    }

    override val whiteListRepository: WhiteListRepository by lazy {
        OfflineWhiteListRepository(NobarDatabase.getDatabase(context).whiteListDao())
    }
}