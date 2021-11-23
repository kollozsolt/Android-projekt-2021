package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.User
import com.example.projectapplication.repository.Repository

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    private val TAG = javaClass.simpleName

    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            token.value = result.token
            Log.d(TAG, "MyApplication - token:  ${MyApplication.token}")
        } catch (e: Exception) {
            Log.d(TAG, "LoginViewModel - exception: ${e}")
        }
    }
}