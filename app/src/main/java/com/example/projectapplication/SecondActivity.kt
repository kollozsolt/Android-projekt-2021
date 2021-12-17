package com.example.projectapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.databinding.ActivityMainBinding
import com.example.projectapplication.fragments.ListFragment
import com.example.projectapplication.fragments.LoginFragment
import com.example.projectapplication.fragments.MyProfileFragment
import com.example.projectapplication.fragments.RegistrationFragment
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.viewmodels.ListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class SecondActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName
    val supportFragment: FragmentManager = supportFragmentManager
    lateinit var navView: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportFragment.beginTransaction()
            .add(R.id.fragment_container, ListFragment()).commit()

        navView = findViewById(R.id.bottom_navigation)
        navView.setItemSelected(R.id.ic_timeline, true)

        navView.setOnItemSelectedListener {
            when(it){
                R.id.ic_timeline -> {
                    replaceFragment(ListFragment())
                }
                R.id.ic_market -> {
                    replaceFragment(MyProfileFragment())
                }
                R.id.ic_fares -> {
                    replaceFragment(MyProfileFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment.javaClass != supportFragmentManager.fragments.last().javaClass){
            val supportFragment: FragmentManager? = supportFragmentManager
            supportFragment?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)?.addToBackStack(null)?.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navView.setItemSelected(R.id.ic_timeline, true)
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