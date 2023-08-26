package com.example.project.data.offline

import com.example.project.data.dao.WhiteListDao
import com.example.project.data.model.WhiteList
import com.example.project.data.repository.WhiteListRepository
import kotlinx.coroutines.flow.Flow

class OfflineWhiteListRepository(private val whiteListDao: WhiteListDao) : WhiteListRepository {
    override fun getAllWhiteList(idUser: WhiteList): Flow<List<WhiteList>> = whiteListDao.getAll(idUser)
    override suspend fun deleteWhiteList(id: Int): Flow<WhiteList> = whiteListDao.delete(id)
    override suspend fun insertWhiteList(whiteList: WhiteList) = whiteListDao.insert(whiteList)
}