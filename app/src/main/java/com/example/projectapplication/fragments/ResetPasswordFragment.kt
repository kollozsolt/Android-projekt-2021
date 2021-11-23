package com.example.projectapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentLoginBinding
import com.example.projectapplication.databinding.FragmentResetPasswordBinding
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.LoginViewModel
import com.example.projectapplication.viewmodels.LoginViewModelFactory
import com.example.projectapplication.viewmodels.ResetPasswordViewModel
import com.example.projectapplication.viewmodels.ResetPasswordViewModelFactory
import kotlinx.coroutines.launch

class ResetPasswordFragment : Fragment() {

    private val TAG = javaClass.simpleName

    private lateinit var resetPasswordViewModel: ResetPasswordViewModel

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEditText: EditText = binding.usernameTextEdit
        val emailEditText: EditText = binding.emailTextEdit
        val resetButton: Button = binding.resetButton

        resetButton.setOnClickListener{resetOnClick(nameEditText, emailEditText)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ResetPasswordViewModelFactory(this.requireContext(), Repository())
        resetPasswordViewModel = ViewModelProvider(this, factory).get(ResetPasswordViewModel::class.java)
    }

    private fun resetOnClick(nameEditText: EditText, emailEditText: EditText) {
        resetPasswordViewModel.user.value.let{
            if(it != null){
                it.username = nameEditText.text.toString()
            }
            if (it != null){
                it.email = emailEditText.text.toString()
            }
        }
        lifecycleScope.launch{
            resetPasswordViewModel.resetPassword()
        }
    }
}