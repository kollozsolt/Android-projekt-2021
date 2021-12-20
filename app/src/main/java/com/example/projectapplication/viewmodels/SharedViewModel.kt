package com.example.projectapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectapplication.model.Product

class SharedViewModel : ViewModel() {
    private var product: Product? = Product()

    fun setProduct(prod: Product?){
        product = prod
    }
    fun getProduct(): Product?{
        return product
    }

}