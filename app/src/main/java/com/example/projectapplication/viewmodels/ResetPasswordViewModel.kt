package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.ResetPasswordRequest
import com.example.projectapplication.model.User
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class ResetPasswordViewModel(val context: Context, val repository: Repository) : ViewModel(){
    private val TAG = javaClass.simpleName

    var user = MutableLiveData<User>()
    var new_password = MutableLiveData<String?>()

    init {
        user.value = User()
        new_password.value = ""
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

    suspend fun updatePassword(){
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "")
        try{
            val result = repository.updatePassword(token, new_password!!.value)
            Log.d(TAG, "MyApplication - message ${result.message}" )
            Toast.makeText(context, "Updating successfully", Toast.LENGTH_LONG).show()
        } catch (e: Exception){
            Log.d(TAG, "ResetPasswordViewModel - exception: ${e}")
            Toast.makeText(context, "Updating unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }
}
