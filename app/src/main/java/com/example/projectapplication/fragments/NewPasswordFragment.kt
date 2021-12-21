package com.example.projectapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.R
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.ResetPasswordViewModel
import com.example.projectapplication.viewmodels.ResetPasswordViewModelFactory
import kotlinx.coroutines.launch


class NewPasswordFragment : Fragment() {
    private lateinit var passwordTextView: TextView
    private lateinit var confirmPasswordTextView: TextView
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ResetPasswordViewModelFactory(this.requireContext(), Repository())
        resetPasswordViewModel = ViewModelProvider(this, factory).get(ResetPasswordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_new_password, container, false)
        val base: FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val backButton: ImageView = header.findViewById(R.id.back_image_view)
        val newPasswordText: TextView = header.findViewById(R.id.new_password_text_view)
        val resetButton: AppCompatButton = viewOwn.findViewById(R.id.reset_password_button)
        passwordTextView = viewOwn.findViewById(R.id.new_password_text_edit)
        confirmPasswordTextView = viewOwn.findViewById(R.id.new_password_again_text_edit)
        backButton.visibility = View.VISIBLE
        newPasswordText.visibility = View.VISIBLE

        resetButton.setOnClickListener{resetClick()}
        backButton.setOnClickListener{backButtonPress()}
        return view
    }

    private fun resetClick(){
        if(isFilled()){
            if(passwordTextView.text.toString() != confirmPasswordTextView.text.toString()){
                confirmPasswordTextView.setError("Passwords are different")
            }
            else{
                resetPasswordViewModel.new_password.let{
                    if(it != null){
                        it.value = confirmPasswordTextView.text.toString()
                    }
                }
                lifecycleScope.launch{
                    resetPasswordViewModel.updatePassword()
                }
//                val supportFragment: FragmentManager? = activity?.supportFragmentManager
//                supportFragment?.popBackStack()
//                supportFragment?.popBackStack()
            }
        }
    }

    private fun isFilled():Boolean{
        var correct = true
        if(passwordTextView.text.isEmpty()){
            passwordTextView.setError("Give a new password!")
            correct = false
        }
        if(confirmPasswordTextView.text.isEmpty()){
            confirmPasswordTextView.setError("Give a confirmation password!")
            correct = false
        }
        return correct
    }

    private fun backButtonPress(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }
}