package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.LoginRequest
import com.example.projectapplication.model.User
import com.example.projectapplication.repository.Repository

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    private val TAG = javaClass.simpleName

    var token: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.KEY_TOKEN ,result.token)
            token.value = result.token
            Log.d(TAG, "LoginViewModel - login response: $result")
        } catch (e: Exception) {
            Toast.makeText(context, "Login Unsuccessfully", Toast.LENGTH_LONG).show()
            Log.d(TAG, "LoginViewModel - exception: ${e}")
        }
    }
}