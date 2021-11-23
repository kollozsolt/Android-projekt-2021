package com.example.projectapplication.repository


import com.example.projectapplication.api.RetrofitInstance
import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.LoginResponse
import com.example.projectapplication.model.ResetPasswordRequest
import com.example.projectapplication.model.ResetPasswordResponse

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse{
        return RetrofitInstance.api.resetPassword(request)
    }
}