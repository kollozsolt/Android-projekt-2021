package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.OrderedProduct
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class AddOrderViewModel(private val context: Context, private val repository: Repository) : ViewModel(){
    val orderedProduct: MutableLiveData<OrderedProduct> = MutableLiveData()

    init{
        orderedProduct.value = OrderedProduct()
    }

    suspend fun orderProduct(){
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "").toString()
        try{
//            Log.d("KAKA"," $token, ${orderedProduct.value!!.title}, ${orderedProduct.value!!.description}, ${orderedProduct.value!!.price_per_unit}, ${orderedProduct.value!!.units}, ${orderedProduct.value!!.owner_username}")
            val result = repository.addOrder(token, orderedProduct.value!!.title, orderedProduct.value!!.description, orderedProduct.value!!.price_per_unit, orderedProduct.value!!.units, orderedProduct.value!!.owner_username)
            Toast.makeText(context, "Launching successfully", Toast.LENGTH_LONG).show()

        } catch (e: Exception){
            Log.d("xxx", "AddOrderViewModelException: ${e.toString()}")
//            Toast.makeText(context, "Launching unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }

}