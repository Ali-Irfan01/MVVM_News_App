package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFirstCoroutineMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstCoroutineMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstCoroutineMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstCoroutineMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(3000L)
            Log.d("TAG", "Coroutine says hello from thread ${Thread.currentThread().name}")
        }

        Log.d("TAG", "Hello from main thread ${Thread.currentThread().name}")

    }
}