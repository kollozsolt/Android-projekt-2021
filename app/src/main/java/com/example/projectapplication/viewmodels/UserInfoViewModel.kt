package com.example.projectapplication.viewmodels

import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Data
import com.example.projectapplication.model.User
import com.example.projectapplication.model.UserInfoResponse
import com.example.projectapplication.repository.Repository
import kotlinx.coroutines.launch

class UserInfoViewModel(private val repository: Repository) : ViewModel() {
    private val TAG = javaClass.simpleName
    var user: MutableLiveData<Data> = MutableLiveData()

    init{
        Log.d(TAG, "ListViewModel constructor - Token: ${SharedPreferencesManager.KEY_TOKEN}")
        getUserInfo()
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            try{
                val name = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_INFO_NAME, "Helikopteres Laura").toString()
                val result = repository.getUserInfo(name)
                user.value = result.data[0]
//                Log.d("KAKA", "${user.value?.username} - ${user.value?.email} - ${user.value?.phone_number}")
            } catch(e: Exception){
                Log.d("xxx", "UserInfo exception: ${e.toString()}")
            }
        }
    }
}