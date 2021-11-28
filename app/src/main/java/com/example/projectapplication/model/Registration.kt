package com.example.projectapplication.model

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password : String,

    @SerializedName("email")
    var email: String,

    @SerializedName("phone_number")
    var phone_number: Int
)

data class RegistrationResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("creation_time")
    var creation_time: Long
)