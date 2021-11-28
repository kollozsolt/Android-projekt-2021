package com.example.projectapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentRegistrationBinding
import com.example.projectapplication.databinding.FragmentResetPasswordBinding
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.RegistrationViewModel
import com.example.projectapplication.viewmodels.RegistrationViewModelFactory
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {

    private val TAG = javaClass.simpleName

    private lateinit var registrationViewModel : RegistrationViewModel

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
        val registrationButton: Button = binding.registrationButton

        loginText.setOnClickListener{loginClick()}

        registrationButton.setOnClickListener{registrationClick(nameEditText, passwordEditText, emailEditText, phoneEditText)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegistrationViewModelFactory(this.requireContext(), Repository())
        registrationViewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
    }

    private fun registrationClick(nameEditText: EditText, passwordEditText: EditText, emailEditText: EditText, phoneEditText: EditText){
        registrationViewModel.user.value.let {
            if (it != null){
                it.username = nameEditText.text.toString()
            }
            if (it != null){
                it.password = passwordEditText.text.toString()
            }
            if (it != null){
                it.email = emailEditText.text.toString()
            }
            if (it != null){
                if (phoneEditText.text.toString() != ""){
                    it.phone_number = phoneEditText.text.toString().toInt()
                } else {
                    it.phone_number = 0
                }
            }
            lifecycleScope.launch{
                registrationViewModel.registration()
            }
        }
    }

    private fun loginClick(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container_view, LoginFragment())?.commit()
    }

}