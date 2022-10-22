package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignupEmailBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignupFragmentEmail : Fragment(R.layout.fragment_signup_email) {

    private lateinit var binding: FragmentSignupEmailBinding
    private lateinit var emailAuthWithFirebase: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupEmailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAuthWithFirebase = FirebaseAuth.getInstance()

        checkState()

        binding.btnSignupNew.setOnClickListener {
            checkState()
            signup()
        }

        binding.txtSignupEmail.setOnClickListener {
            emailAuthWithFirebase.signOut()
            checkState()
        }

    }

    private fun signup() {
            val email = binding.edtSignupEmailNew.text.toString()
            val password = binding.edtPasswordSignupNew.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        emailAuthWithFirebase.createUserWithEmailAndPassword(email, password).await()
                        checkState()
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else
                Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
    }

    private fun checkState() {
        if (emailAuthWithFirebase.currentUser == null) {
            binding.txtSignupEmail.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.errorColor
                )
            )
        } else {
            binding.txtSignupEmail.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    androidx.appcompat.R.color.abc_btn_colored_text_material
                )
            )
            binding.txtSignupEmail.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }


}