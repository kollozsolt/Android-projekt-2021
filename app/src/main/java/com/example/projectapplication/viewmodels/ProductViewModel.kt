package com.example.projectapplication.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    private val TAG = javaClass.simpleName

    init{
        Log.d(TAG, "ListViewModel constructor - Token: ${SharedPreferencesManager.KEY_TOKEN}")
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            val token = MyApplication.sharedPreferences.getStringValue( SharedPreferencesManager.KEY_TOKEN, "" )
            val limit = MyApplication.sharedPreferences.getStringValue( SharedPreferencesManager.LIMIT, "150" )
            val filter = MyApplication.sharedPreferences.getStringValue( SharedPreferencesManager.FILTER, "" )
            val sort = MyApplication.sharedPreferences.getStringValue( SharedPreferencesManager.SORT, "" )
            val skip = MyApplication.sharedPreferences.getStringValue( SharedPreferencesManager.SKIP, "0" )
            try {
                val result = repository.getProducts(token, limit?.toInt(), filter , sort, skip?.toInt())

                products.value = result?.products
                Log.d("xxx", "ListViewModel - #products:  ${result?.item_count}")
            }
            catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }

}