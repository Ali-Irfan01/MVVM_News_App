package com.example.myapplication.loginWithMultipleOptions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGoogleBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton.SIZE_STANDARD
import com.google.android.gms.common.SignInButton.SIZE_WIDE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GoogleFragment : Fragment(R.layout.fragment_google) {

    private lateinit var binding: FragmentGoogleBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoogleBinding.inflate(layoutInflater, container, false)    // Inflate the layout for this fragment.in
        auth = FirebaseAuth.getInstance()
        googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoogleLogin.setSize(SIZE_WIDE)
        checkState()
        val sigInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                if(result.resultCode < 0) {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
                    account?.let {
                        googleAuthForFirebase(it)
                    }
                }
        })

        binding.btnGoogleLogin.setOnClickListener {
            mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
            sigInLauncher.launch(mGoogleSignInClient.signInIntent)
            checkState()
        }

        binding.btnSignOutGoogle.setOnClickListener {
            auth.signOut()
            checkState()
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully Signed In", Toast.LENGTH_SHORT).show()
                    checkState()
                }
            }   catch (e: Exception)    {
                withContext(Dispatchers.Main)   {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkState(){
        if(auth.currentUser != null)   {
            binding.txtEmailGoogle.text = auth.currentUser!!.email
            binding.txtStatusGoogle.text = "Logged In"
        }   else    {
            binding.txtEmailGoogle.text = ""
            binding.txtStatusGoogle.text = ""
        }
    }
}