package com.example.projectapplication.model

import com.google.gson.annotations.SerializedName

data class User(var username: String="", var password: String="", var email: String="", var phone_number: Int=0)
