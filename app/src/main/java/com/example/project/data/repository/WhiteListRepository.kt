package com.example.project.data.repository

import com.example.project.data.model.WhiteList
import kotlinx.coroutines.flow.Flow

interface WhiteListRepository {
    fun getAllWhiteList(idUser: WhiteList): Flow<List<WhiteList>>

    suspend fun insertWhiteList(whiteList: WhiteList)

    suspend fun deleteWhiteList(id: Int): Flow<WhiteList>
}