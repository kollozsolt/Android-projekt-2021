package com.example.projectapplication.repository


import com.example.projectapplication.api.RetrofitInstance
import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.LoginResponse

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }
}