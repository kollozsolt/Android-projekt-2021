package com.example.projectapplication.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.SecondActivity
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
            MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.USER_NAME ,result.username)
            MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.USER_INFO_NAME ,result.username)
            token.value = result.token
            Log.d(TAG, "LoginViewModel - login response: $result")
            Log.d(TAG, "navigate to list")
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()

            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Login Unsuccessfully", Toast.LENGTH_LONG).show()
            Log.d(TAG, "LoginViewModel - exception: ${e}")
        }
    }
}