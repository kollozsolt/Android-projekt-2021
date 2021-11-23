package com.example.projectapplication.api

import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.LoginResponse
import com.example.projectapplication.model.ResetPasswordRequest
import com.example.projectapplication.model.ResetPasswordResponse
import com.example.projectapplication.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse
}