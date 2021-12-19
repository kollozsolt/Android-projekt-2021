package com.example.projectapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.manager.SharedPreferencesManager
import okhttp3.internal.wait

class MyProfileFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_my_profile, container, false)
        val base: FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val profilePicture: ImageView = header.findViewById(R.id.profile_image_view)
        val backButton: ImageView = header.findViewById(R.id.back_image_view)
        val settingText: TextView = header.findViewById(R.id.settings_text_view)
        val scrollView: ScrollView = view.findViewById(R.id.profile_settings_scroll_view)
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val emailEditText: EditText = view.findViewById(R.id.email_text_edit)
        val nameEditText: EditText = view.findViewById(R.id.username_text_edit)
        val phoneEditText: EditText = view.findViewById(R.id.phone_text_edit)

        Log.d("KAKA12", "${userInfoViewModel.user.value?.email} - ${userInfoViewModel.user.value?.phone_number} - ${userInfoViewModel.user.value?.username}")

        nameTextView.text = userInfoViewModel.user.value?.username
        emailEditText.setText(userInfoViewModel.user.value?.email, TextView.BufferType.EDITABLE)
        nameEditText.setText(userInfoViewModel.user.value?.username, TextView.BufferType.EDITABLE)
        phoneEditText.setText(userInfoViewModel.user.value?.phone_number.toString(), TextView.BufferType.EDITABLE)

        profilePicture.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
        settingText.visibility = View.VISIBLE

        backButton.setOnClickListener{backButtonPress()}
        return view
    }


    private fun backButtonPress(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }
}