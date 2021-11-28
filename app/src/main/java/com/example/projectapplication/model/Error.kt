package com.example.projectapplication.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("timestamp")
    var timestamp: Long
)