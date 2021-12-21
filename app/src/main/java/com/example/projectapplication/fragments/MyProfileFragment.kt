package com.example.projectapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.UserUpdateBody
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.*
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MyProfileFragment : BaseFragment() {
    private lateinit var emailEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText

    private lateinit var updateUserViewModel: UpdateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UpdateUserViewModelFactory(this.requireContext(), Repository())
        updateUserViewModel = ViewModelProvider(this, factory).get(UpdateUserViewModel::class.java)
    }

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
        emailEditText = view.findViewById(R.id.email_text_edit)
        nameEditText = view.findViewById(R.id.username_text_edit)
        phoneEditText = view.findViewById(R.id.phone_text_edit)
        val updatePasswordButton: Button = view.findViewById(R.id.reset_password_button)
        val updateUserDataButton: Button = view.findViewById(R.id.update_profile_button)

        userInfoViewModel.user.observe(viewLifecycleOwner){
            nameTextView.text = userInfoViewModel.user.value?.username
            emailEditText.setText(userInfoViewModel.user.value?.email, TextView.BufferType.EDITABLE)
            nameEditText.setText(userInfoViewModel.user.value?.username, TextView.BufferType.EDITABLE)
            phoneEditText.setText(userInfoViewModel.user.value?.phone_number.toString(), TextView.BufferType.EDITABLE)
            if(userInfoViewModel.user.value?.username != MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "")){
                updatePasswordButton.visibility = View.GONE
                updateUserDataButton.visibility = View.GONE
                settingText.text = "Profile"
                nameEditText.isEnabled = false
                phoneEditText.isEnabled = false
            } else{
                updateUserDataButton.setOnClickListener{updateClick()}
            }
        }

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

    private fun updateClick(){
        val updateBody = UserUpdateBody()
        if(isFilled()){
            updateBody.email = emailEditText.text.toString()
            updateBody.phone_number = phoneEditText.text.toString().toInt()
            updateBody.username = nameEditText.text.toString()
            updateUserViewModel.updateBody.value.let {
                if(it!=null) {
                    it.username = updateBody.username
                }
                if(it!=null){
                    it.phone_number = updateBody.phone_number
                }
                if(it!=null){
                    it.email = updateBody.email
                }
            }
            lifecycleScope.launch {
                updateUserViewModel.updateUserData()
            }
//            val supportFragment: FragmentManager? = activity?.supportFragmentManager
//            supportFragment?.popBackStack()
        }
    }

    private fun isFilled():Boolean{
        var correct = true
        if (nameEditText.text.isEmpty()){
            nameEditText.setError("Give your username")
            correct = false
        }
        if (phoneEditText.text.isEmpty()){
            phoneEditText.setError("Give your phone number")
            correct = false
        }
        return correct
    }
}