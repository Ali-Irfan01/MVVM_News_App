package com.example.myapplication.firebaseAuth

import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFirebaseAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuth : AppCompatActivity() {

    lateinit var binding: ActivityFirebaseAuthBinding
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val memoryInfo = getAvailableMemory()
        Log.d("TAG", "Total Memory: ${memoryInfo.totalMem / 1048576L} mb")
        Log.d("TAG", "Available Memory: ${memoryInfo.availMem / 1048576L} mb")
        Log.d("TAG", "Memory Threshold: ${memoryInfo.threshold / 1048576L} mb")
        Log.d("TAG", "Memory Low: ${memoryInfo.lowMemory}")
        val runtime = Runtime.getRuntime()
        Log.d("TAG", "max Heap: ${runtime.maxMemory() / 1048576L} mb")
        Log.d("TAG", "Native Heap: ${Debug.getNativeHeapSize() / 1048576L} mb")
        Log.d("TAG", "Native Free Heap: ${Debug.getNativeHeapFreeSize() / 1048576L} mb")
        Log.d("TAG", "Native Heap Allocated: ${Debug.getNativeHeapAllocatedSize() / 1048576L} mb")


        runtime.maxMemory()

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            checkLoggedInState()
        }

        binding.ivProfilePicture.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.btnUpdateUser.setOnClickListener {
            updateUser()
        }

        binding.btnClearImage.setOnClickListener {
            binding.ivProfilePicture.setImageURI(null)
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    private fun registerUser() {
        val email = binding.edtRegisterEmail.text.toString()
        val password = binding.edtRegisterPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@FirebaseAuth, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun loginUser() {
        val email = binding.edtEmailLogin.text.toString()
        val password = binding.edtPasswordLogin.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@FirebaseAuth, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                binding.ivProfilePicture.setImageURI(imageUri)
            } else
                Log.e("TAG", "Image can not be loaded").also { imageUri = null }
        }

    private fun updateUser() {
        val user = auth.currentUser
        if (user == null) {
            Log.e("TAG", "User not logged in can not update changes")
        } else {
            val userName: String? = binding.edtUpdateUsername.toString()
            if (userName != "") {
                if (imageUri != null) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .setPhotoUri(imageUri)
                        .build()

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            user.updateProfile(profileUpdates).await()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@FirebaseAuth,
                                    "Updated user Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@FirebaseAuth, e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                } else {
                    Log.e("TAG", "Unable to parse the image")
                }
            } else {
                Log.e("TAG", "Please enter valid name")
            }
        }
    }

    private fun checkLoggedInState(): Boolean {
        if (auth.currentUser == null) {
            binding.txtStatus.text = "You are not Logged in"
            return false
        } else {
            binding.txtStatus.text = "You are Logged in"
            return true
        }
    }


    private fun getAvailableMemory(): ActivityManager.MemoryInfo {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return ActivityManager.MemoryInfo().also { memoryInfo ->
            activityManager.getMemoryInfo(memoryInfo)
        }
    }
}