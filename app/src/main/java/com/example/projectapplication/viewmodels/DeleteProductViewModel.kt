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

class DeleteProductViewModel (val context: Context, val repository: Repository) : ViewModel(){
    var product_id : MutableLiveData<String> = MutableLiveData()

    init {
        product_id.value = String()
    }

    suspend fun deleteProduct(){
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "").toString()
        try{
            val result = repository.deleteProduct(token, product_id.value.toString())
            Log.d("KAKA", "$token - ${product_id.value}")
//            Toast.makeText(context, "Launching successfully", Toast.LENGTH_LONG).show()

        } catch (e: Exception){
            Log.d("xxx", "DeleteProductViewModelException: ${e.toString()}")
//            Toast.makeText(context, "Launching unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }
}