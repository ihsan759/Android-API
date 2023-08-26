package com.example.project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project.data.model.User
import com.example.project.data.dao.UserDao
import com.example.project.data.dao.WhiteListDao
import com.example.project.data.model.WhiteList

@Database(entities = [User::class, WhiteList::class], version = 1, exportSchema = false)
abstract class NobarDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun whiteListDao(): WhiteListDao

    companion object {
        @Volatile
        private var Instance: NobarDatabase? = null

        fun getDatabase(context: Context): NobarDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NobarDatabase::class.java, "nobar")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}