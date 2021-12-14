package com.example.projectapplication.repository


import com.example.projectapplication.api.RetrofitInstance
import com.example.projectapplication.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse{
        return RetrofitInstance.api.resetPassword(request)
    }

    suspend fun registration(request: RegistrationRequest) : RegistrationResponse {
        return RetrofitInstance.api.registration(request)
    }

    suspend fun getProducts(token: String) : ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}