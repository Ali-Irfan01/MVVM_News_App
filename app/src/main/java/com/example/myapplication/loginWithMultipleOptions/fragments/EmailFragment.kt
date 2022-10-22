package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmailBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EmailFragment : Fragment(R.layout.fragment_email) {

    private lateinit var binding: FragmentEmailBinding
    private lateinit var emailAuthWithFirebase: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signupText = getString(R.string.signup_text)
        binding.txtSignup.movementMethod = LinkMovementMethod.getInstance()
        binding.txtSignup.setText(signupText, TextView.BufferType.SPANNABLE)
        val mySpannable = binding.txtSignup.text as Spannable
        val myClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_emailFragment_to_signupFragmentEmail)
            }
        }
        mySpannable.setSpan(myClickableSpan, 29, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        emailAuthWithFirebase = FirebaseAuth.getInstance()

        checkState()

        binding.btnLoginNew.setOnClickListener {
            checkState()
            login()
        }

        binding.txtLoginEmail.setOnClickListener {
            emailAuthWithFirebase.signOut()
            checkState()
        }
    }

    private fun login() {
        if (emailAuthWithFirebase.currentUser == null) {
            val email = binding.edtLoginEmailNew.text.toString()
            val password = binding.edtPasswordLoginNew.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        emailAuthWithFirebase.signInWithEmailAndPassword(email, password).await()
                        checkState()
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else
                Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(
                context,
                "Already logged in!",
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun checkState() {
        if (emailAuthWithFirebase.currentUser == null) {
            binding.txtLoginEmail.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.errorColor
                )
            )
        } else {
            binding.txtLoginEmail.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    androidx.appcompat.R.color.abc_btn_colored_text_material
                )
            )
            binding.txtLoginEmail.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

}