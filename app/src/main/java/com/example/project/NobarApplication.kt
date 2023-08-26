package com.example.project

import android.app.Application
import com.example.project.data.AppContainer
import com.example.project.data.AppDataContainer

class NobarApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}