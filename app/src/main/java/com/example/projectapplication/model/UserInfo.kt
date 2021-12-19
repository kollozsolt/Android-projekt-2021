package com.example.projectapplication.model

data class Data(
    var username: String="",
    var phone_number: Int=0,
    var email: String="",
    var firebase_token: String="",
    var is_activated: Boolean=true,
    var creation_time: String=""
)

data class UserInfoResponse(
    var code: Int = 0,
    var data: List<Data> = emptyList(),
    var timestamp: Long = 0
)