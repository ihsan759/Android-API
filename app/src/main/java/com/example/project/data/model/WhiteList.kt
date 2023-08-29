package com.example.project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WhiteList")
class WhiteList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idUser: Int,
    val idFilm: Int
)