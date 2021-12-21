package com.example.projectapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectapplication.repository.Repository

class AddProductViewModelFactory(private val context: Context, val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddProductViewModel(context, repository) as T
    }

}