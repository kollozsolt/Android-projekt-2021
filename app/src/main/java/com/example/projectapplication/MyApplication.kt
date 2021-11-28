package com.example.projectapplication

import android.app.Application
import com.example.projectapplication.manager.SharedPreferencesManager

class MyApplication : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPreferencesManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = SharedPreferencesManager(applicationContext)
    }
}