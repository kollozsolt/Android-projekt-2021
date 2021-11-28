package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.model.RegistrationRequest
import com.example.projectapplication.model.User
import com.example.projectapplication.repository.Repository

class RegistrationViewModel(val context: Context, val repository: Repository): ViewModel() {
    private val TAG = javaClass.simpleName

    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun registration() {
        val request = RegistrationRequest(
            username = user.value!!.username, password = user.value!!.password,
            email = user.value!!.email, phone_number = user.value!!.phone_number
        )
        try {
            val result = repository.registration(request)
            Log.d(TAG, "MyApplication - message ${result.message}")
        } catch (e: Exception) {
            Toast.makeText(context, "Registration Unsuccessfully", Toast.LENGTH_LONG).show()
            Log.d(TAG, "RegistrationViewModel - exception: ${e}")
        }
    }
}