package com.example.projectapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        topAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)

        val image = binding.logoImageView;
        val text = binding.nameTextView;
        val poweredText = binding.poweredTextView;
        val smallLogo = binding.smallLogoImageView;

        image.animation = topAnimation
        text.animation = bottomAnimation
        poweredText.animation = bottomAnimation
        smallLogo.animation = bottomAnimation
    }
}