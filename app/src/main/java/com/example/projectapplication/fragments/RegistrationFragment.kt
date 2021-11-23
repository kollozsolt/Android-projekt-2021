package com.example.projectapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentRegistrationBinding
import com.example.projectapplication.databinding.FragmentResetPasswordBinding

class RegistrationFragment : Fragment() {

    private val TAG = javaClass.simpleName

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEditText: EditText = binding.usernameTextEdit
        val passwordEditText: EditText = binding.passwordTextEdit
        val emailEditText: EditText = binding.emailTextEdit
        val phoneEditText: EditText = binding.phoneTextEdit
        val loginText : TextView = binding.alreadyTextClick

        loginText.setOnClickListener{loginClick()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun loginClick(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container_view, LoginFragment())?.commit()
    }

}