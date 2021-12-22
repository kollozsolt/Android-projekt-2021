package com.example.projectapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.fragments.MyFaresFragment
import com.example.projectapplication.fragments.ProductFragment
import com.example.projectapplication.fragments.MyProfileFragment
import com.example.projectapplication.manager.SharedPreferencesManager
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class SecondActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName
    val supportFragment: FragmentManager = supportFragmentManager
    lateinit var navView: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "")
        supportFragment.beginTransaction()
            .add(R.id.fragment_container, ProductFragment()).commit()

        navView = findViewById(R.id.bottom_navigation)
        navView.setItemSelected(R.id.ic_timeline, true)

        navView.setOnItemSelectedListener {
            when(it){
                R.id.ic_timeline -> {
                    MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "")
                    replaceFragment(ProductFragment())
                }
                R.id.ic_market -> {
                    val name = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "")
                    Log.d("KAKA", "{\"username\"}:{\"$name\"}")
                    MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "{\"username\":\"$name\"}")
                    replaceFragment(ProductFragment())
                }
                R.id.ic_fares -> {
                    replaceFragment(MyFaresFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val supportFragment: FragmentManager? = supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)?.addToBackStack(null)?.commit()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        navView.setItemSelected(R.id.ic_timeline, true)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called!")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "{}")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.SORT, "{}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called!")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "{}")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.SORT, "{}")
    }
}