package com.example.projectapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.databinding.ActivityMainBinding
import com.example.projectapplication.fragments.ListFragment
import com.example.projectapplication.fragments.LoginFragment
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.viewmodels.ListViewModel

class SecondActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName
    val supportFragment: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportFragment.beginTransaction()
            .add(R.id.fragment_container, ListFragment()).commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called!")
    }
}