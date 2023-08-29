package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.project.data.database.AppDatabase
import com.example.project.ui.sideBar.Drawer
import com.example.project.ui.theme.ProjectTheme


class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
                    .build()
                Drawer(database)
            }
        }
    }
}

