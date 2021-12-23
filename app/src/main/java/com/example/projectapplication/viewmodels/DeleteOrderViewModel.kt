package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class DeleteOrderViewModel(private val context: Context, private val repository: Repository) : ViewModel() {
    var order_id : MutableLiveData<String> = MutableLiveData()

    init {
        order_id.value = String()
    }

    suspend fun deleteOrder(){
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "").toString().replace("\"", "")
        try{
            val result = repository.deleteOrder(token, order_id.value.toString())
            Log.d("KAKA", "$token - ${order_id.value}")
            Toast.makeText(context, "Launching successfully", Toast.LENGTH_LONG).show()

        } catch (e: Exception){
            Log.d("xxx", "DeleteProductViewModelException: ${e.toString()}")
            Toast.makeText(context, "Launching unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }

}