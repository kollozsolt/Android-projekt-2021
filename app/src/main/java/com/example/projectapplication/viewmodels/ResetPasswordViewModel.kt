package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.model.ResetPasswordRequest
import com.example.projectapplication.model.User
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class ResetPasswordViewModel(val context: Context, val repository: Repository) : ViewModel(){
    private val TAG = javaClass.simpleName

    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun resetPassword(){
        val request = ResetPasswordRequest(username = user.value!!.username, email = user.value!!.email)
        try{
            val result = repository.resetPassword(request)
            Log.d(TAG, "MyApplication - message ${result.message}" )
        } catch (e: Exception){
            Log.d(TAG, "ResetPasswordViewModel - exception: ${e}")
        }
    }
}