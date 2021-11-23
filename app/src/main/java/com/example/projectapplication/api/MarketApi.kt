package com.example.projectapplication.api

import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.LoginResponse
import com.example.projectapplication.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest) : LoginResponse
}