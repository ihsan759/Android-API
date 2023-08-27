package com.example.project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val username: String,
    val password: String
)