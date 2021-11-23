package com.example.projectapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentLoginBinding
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.LoginViewModel
import com.example.projectapplication.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private lateinit var loginViewModel: LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEditText: EditText = binding.usernameTextEdit
        val passwordEditText: EditText = binding.passwordTextEdit
        val loginButton: Button = binding.loginButton
        val forgotText: TextView = binding.forgotTextClick

        loginButton.setOnClickListener { loginOnClick(nameEditText, passwordEditText) }

        forgotText.setOnClickListener { forgotTextClick() }

        loginViewModel.token.observe(viewLifecycleOwner) {
            Log.d(TAG, "navigate to list")
            Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    private fun loginOnClick(nameEditText: EditText, passwordEditText: EditText) {
        loginViewModel.user.value.let {
            if (it != null) {
                it.username = nameEditText.text.toString()
            }
            if (it != null) {
                it.password = passwordEditText.text.toString()
            }
        }
        lifecycleScope.launch {
            loginViewModel.login()
        }
    }

    private fun forgotTextClick() {
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container_view, ResetPasswordFragment())?.commit()
    }

}