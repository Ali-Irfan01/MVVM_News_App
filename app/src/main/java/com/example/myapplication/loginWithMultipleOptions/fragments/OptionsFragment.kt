package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment(R.layout.fragment_options) {

    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEmail.setOnClickListener {
            findNavController().navigate(R.id.action_emailFragment2_to_emailFragment)
        }

        binding.btnGoogle.setOnClickListener {
            findNavController().navigate(R.id.action_emailFragment2_to_googleFragment)
        }

        binding.btnFacebook.setOnClickListener {
            findNavController().navigate(R.id.action_emailFragment2_to_facebookFragment)
        }

        binding.btnApple.setOnClickListener {
            findNavController().navigate(R.id.action_emailFragment2_to_appleFragment)
        }
    }
}