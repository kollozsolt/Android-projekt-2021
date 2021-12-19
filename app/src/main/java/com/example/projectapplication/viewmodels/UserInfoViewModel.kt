package com.example.projectapplication.viewmodels

import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        user.value = Data()
        Log.d(TAG, "ListViewModel constructor - Token: ${SharedPreferencesManager.KEY_TOKEN}")
    }

    suspend fun getUserInfo(){
        try{
            val result = repository.getUserInfo(user.value!!.username)
            user.value.let {
                if (it != null) {
                    it.phone_number = result.data[0].phone_number
                    it.email = result.data[0].email
                }
            }

            Log.d("KAKA", "${user.value?.username} - ${user.value?.email} - ${user.value?.phone_number}")
        } catch(e: Exception){
            Log.d("xxx", "UserInfo exception: ${e.toString()}")
        }
    }
}