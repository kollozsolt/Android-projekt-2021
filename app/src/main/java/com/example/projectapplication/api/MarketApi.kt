package com.example.projectapplication.api

import com.example.projectapplication.model.*
import com.example.projectapplication.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @POST(Constants.REGISTRATION_URL)
    suspend fun registration(@Body request: RegistrationRequest) : RegistrationResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String) : ProductResponse
//                            @Header("limit") limit:(first: String, second: String),
//                            @Header("filter") filter: first: String, second: String),
//                            @Header("sort") sort: (first: String, second: String),
//                            @Header("skip") skip: Int):ProductResponse
}