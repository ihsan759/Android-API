package com.example.project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.data.model.WhiteList
import kotlinx.coroutines.flow.Flow

@Dao
interface WhiteListDao{
    @Query("SELECT * FROM whitelist WHERE idUser = :idUser")
    fun getAll(idUser: Int): WhiteList?

    @Insert
    suspend fun insert(whiteList: WhiteList)

    @Query("Delete FROM whitelist WHERE id = :id")
    suspend fun delete(id: Int): Int
}