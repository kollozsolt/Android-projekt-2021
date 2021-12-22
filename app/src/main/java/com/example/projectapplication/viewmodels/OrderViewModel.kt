package com.example.projectapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Order
import com.example.projectapplication.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: Repository) : ViewModel() {
    var orders: MutableLiveData<List<Order>> = MutableLiveData()
    private val TAG = javaClass.simpleName

    init {
        Log.d(TAG, "ListViewModel constructor - Token: ${SharedPreferencesManager.KEY_TOKEN}")
        getOrders()
    }

    private fun getOrders() {
        viewModelScope.launch {
            val token = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN,
                ""
            )
            val limit = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.ORDER_LIMIT,
                "500"
            )
            val filter = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.ORDER_FILTER,
                "{}"
            )
            val sort = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.ORDER_SORT,
                "{}"
            )
            try {
                val result = repository.getOrders(token, limit?.toInt(), filter, sort)

                orders.value = result?.orders
                Log.d("xxx", "ORDERSViewModel - #products:  ${result?.item_count}")
            } catch (e: Exception) {
                Log.d("xxx", "ORDERSViewModel exception: ${e}")
            }
        }
    }
}