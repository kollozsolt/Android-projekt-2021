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

class ListViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    private val TAG = javaClass.simpleName

    init{
        Log.d(TAG, "ListViewModel constructor - Token: ${SharedPreferencesManager.KEY_TOKEN}")
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val result = MyApplication.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN, ""
                )?.let { repository.getProducts(it) }

                products.value = result?.products
                Log.d("xxx", "ListViewModel - #products:  ${result?.item_count}")
            }
            catch(e: Exception){
                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")
            }
        }
    }

}