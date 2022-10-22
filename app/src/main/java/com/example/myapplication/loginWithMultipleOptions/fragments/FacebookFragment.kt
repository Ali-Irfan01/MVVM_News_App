package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFacebookBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookFragment : Fragment(R.layout.fragment_facebook) {

    private lateinit var binding: FragmentFacebookBinding
    private lateinit var callbackManager: CallbackManager
    private var status: Boolean = false
    private var accessToken: AccessToken? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFacebookBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FacebookSdk.sdkInitialize(requireContext())
        callbackManager = CallbackManager.Factory.create()

        /**
         * Code to get SHA1 encoded in base64 for this package
        try {
            val info = activity!!.packageManager.getPackageInfo(
                activity!!.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
         */

        LoginManager.getInstance().registerCallback(callbackManager, object:
            FacebookCallback<LoginResult> {
            override fun onCancel() {
                Toast.makeText(requireContext(), "You canceled the login process", Toast.LENGTH_SHORT).show()
            }
            override fun onError(error: FacebookException) {
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                Log.e("TAG", error.message.toString())
            }
            override fun onSuccess(result: LoginResult) {
                Toast.makeText(requireContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show()
                Log.d("TAG", result.accessToken.token)
                Log.d("TAG", result.authenticationToken.toString())
            }
        })

        binding.facebookSignIn.setOnClickListener {
            updateStatus()
            if(!status)
                LoginManager.getInstance().logInWithReadPermissions(this, callbackManager, listOf("public_profile"))
            else
                Toast.makeText(requireContext(), "Already Logged in!", Toast.LENGTH_SHORT).show()
        }
        binding.btnStatusFacebook.setOnClickListener {
            updateStatus()
            if(status)
                Toast.makeText(requireContext(), "Logged in!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(requireContext(), "Not logged in!", Toast.LENGTH_SHORT).show()
        }
        binding.btnSignoutFacebook.setOnClickListener {
            updateStatus()
            if(status)
            LoginManager.getInstance().logOut().also {
                Toast.makeText(requireContext(), "Sign out successful", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(requireContext(), "Not logged in!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStatus()  {
        accessToken = AccessToken.getCurrentAccessToken()
        status = accessToken != null && !accessToken!!.isExpired
    }
}

