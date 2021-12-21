package com.example.projectapplication.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.fragments.ProductDetailFragment
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import java.lang.Exception

class AddProductViewModel (val context: Context, val repository: Repository) : ViewModel(){
    var product : MutableLiveData<Product> = MutableLiveData()

    init {
        product.value = Product()
    }

    suspend fun addProduct(){
        val token = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "").toString()
        try{
//            Log.d("KAKA"," $token, ${product.value!!.title}, ${product.value!!.description}, ${product.value!!.price_per_unit}, ${product.value!!.units}, ${product.value!!.is_active}, ${product.value!!.amount_type}, ${product.value!!.price_type}, ${product.value!!.rating}")
            val result = repository.addProduct(token, product.value!!.title, product.value!!.description, product.value!!.price_per_unit, product.value!!.units,
                                                product.value!!.is_active, product.value!!.amount_type, product.value!!.price_type, product.value!!.rating)
//            Toast.makeText(context, "Launching successfully", Toast.LENGTH_LONG).show()

        } catch (e: Exception){
            Log.d("xxx", "AddProductViewModelException: ${e.toString()}")
//            Toast.makeText(context, "Launching unsuccessfully", Toast.LENGTH_LONG).show()
        }
    }
}