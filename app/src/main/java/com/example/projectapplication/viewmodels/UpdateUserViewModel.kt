package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.UserUpdateBody
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class UpdateUserViewModel(private val context: Context, private val repository: Repository) : ViewModel() {
    var updateBody: MutableLiveData<UserUpdateBody> = MutableLiveData()

    init{
        updateBody.value = UserUpdateBody()
    }

    suspend fun updateUserData() {
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "").toString()
        try{
//            Log.d("KAKA", "$token, ${updateBody.value!!.phone_number}, ${updateBody.value!!.email}, ${updateBody.value!!.username}")
            val result = repository.updateUserData(token, updateBody.value!!.phone_number, updateBody.value!!.email, updateBody.value!!.username)
            MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.KEY_TOKEN, result.updatedData[0].token)
            MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.USER_NAME, result.updatedData[0].username)
            Toast.makeText(context, "Launching successfully", Toast.LENGTH_LONG).show()
        } catch (e: Exception){
            Log.d("xxx", "UpdateUserViewModelException: ${e.toString()}")
            Toast.makeText(context, "Launching unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }
}