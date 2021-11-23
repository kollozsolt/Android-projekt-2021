package com.example.projectapplication

import android.app.Application

class MyApplication:Application() {
    companion object{
        var token: String = ""
    }
}