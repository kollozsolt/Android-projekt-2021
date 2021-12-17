package com.example.projectapplication.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.manager.SharedPreferencesManager

class MyProfileFragment : ProductBaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_base, container, false)
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val profilePicture: ImageView = header.findViewById(R.id.profile_image_view)
        val backButton: ImageView = header.findViewById(R.id.back_image_view)
        val settingText: TextView = header.findViewById(R.id.settings_text_view)
        val scrollView: ScrollView = view.findViewById(R.id.profile_settings_scroll_view)
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val userName = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "Helikopteres Laura")

        profilePicture.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
        settingText.visibility = View.VISIBLE
        nameTextView.text = userName.toString()

        backButton.setOnClickListener{backButtonPress()}

        return view
    }

    private fun backButtonPress(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }
}