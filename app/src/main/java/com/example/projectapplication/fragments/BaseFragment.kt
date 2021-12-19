package com.example.projectapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.LoginViewModel
import com.example.projectapplication.viewmodels.LoginViewModelFactory
import com.example.projectapplication.viewmodels.UserInfoViewModel
import com.example.projectapplication.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.internal.wait

abstract class BaseFragment : Fragment() {

    protected lateinit var userInfoViewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        return view;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserInfoViewModelFactory(Repository())
        userInfoViewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)
        val name = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "Helikopteres Laura").toString()
        userInfoViewModel.user.value.let{
            if (it != null) {
                it.username = name
            }
        }
        lifecycleScope.launch {
            userInfoViewModel.getUserInfo()
        }
    }

}