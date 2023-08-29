package com.example.project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.data.model.WhiteList

@Dao
interface WhiteListDao{
    @Query("SELECT * FROM whitelist WHERE idUser = :idUser")
    suspend fun getAll(idUser: Int): List<WhiteList>?

    @Insert
    suspend fun insert(whiteList: WhiteList)

    @Query("Delete FROM whitelist WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT * FROM whitelist WHERE idFilm = :idFilm AND idUser = :idUser")
    suspend fun get(idFilm: Int, idUser: Int): WhiteList?
}