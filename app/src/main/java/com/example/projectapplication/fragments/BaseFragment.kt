package com.example.projectapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.projectapplication.R
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.UserInfoViewModel
import com.example.projectapplication.viewmodels.UserInfoViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseFragment : Fragment() {

    lateinit var userInfoViewModel: UserInfoViewModel

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

//        Log.d("KAKA12", "${userInfoViewModel.user.value?.email} - ${userInfoViewModel.user.value?.phone_number} - ${userInfoViewModel.user.value?.username}")
    }

}