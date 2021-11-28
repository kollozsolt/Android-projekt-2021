package com.example.projectapplication.model

import com.google.gson.annotations.SerializedName


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
