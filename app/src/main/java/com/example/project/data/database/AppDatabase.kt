package com.example.project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project.data.model.User
import com.example.project.data.dao.UserDao
import com.example.project.data.dao.WhiteListDao
import com.example.project.data.model.WhiteList

@Database(entities = [User::class, WhiteList::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun whiteList(): WhiteListDao
}