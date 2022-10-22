package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAppleBinding

class AppleFragment : Fragment(R.layout.fragment_apple) {

    private lateinit var binding: FragmentAppleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppleBinding.inflate(layoutInflater)
        return binding.root
    }
}