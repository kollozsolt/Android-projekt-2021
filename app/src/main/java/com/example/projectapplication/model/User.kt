package com.example.projectapplication.model

import com.google.gson.annotations.SerializedName

data class User(var username: String="", var password: String="", var email: String="", var phone_number: String="")

//GSon converter
data class LoginRequest (
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String
)


data class LoginResponse (
    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("phone_number")
    var phone_number: Int,

    @SerializedName("token")
    var token: String,

    @SerializedName("creation_time")
    var creation_time: Long,

    @SerializedName("refresh_time")
    var refresh_time: Long
)

data class ResetPasswordRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String
)

data class ResetPasswordResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("timestamp")
    var timestamp: Long
)


data class ErrorResponse (
    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("timestamp")
    var timestamp: Long
)