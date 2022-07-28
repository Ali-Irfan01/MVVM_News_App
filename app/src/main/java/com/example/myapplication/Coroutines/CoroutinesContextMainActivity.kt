package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCoroutinesContextMainBinding
import kotlinx.coroutines.*

class CoroutinesContextMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesContextMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesContextMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("TAG", "Before starting coroutine in Thread: ${Thread.currentThread().name}")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("TAG", "Starting coroutine in Thread: Dispatchers.IO ${Thread.currentThread().name}")
            val result = performNetworkCall()
            withContext(Dispatchers.Main){
                binding.tvCorContext.text = result
            }
        }
    }
    suspend fun performNetworkCall(): String{

        Log.d("TAG", "Performing network call in Thread: ${Thread.currentThread().name}")
        delay(1000L)
        return "Message Received"
    }
}