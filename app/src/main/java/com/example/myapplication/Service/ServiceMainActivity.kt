package com.example.myapplication.Service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityServiceMainBinding

class ServiceMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    binding.btnstartservice.setOnClickListener {
        Intent(this, MyIntentService::class.java).also {
            startService(it)
            binding.tvService.text = "Service running"
        }
    }

        binding.btnstopservice.setOnClickListener {
            MyIntentService.stopService()
            binding.tvService.text = "Service Stopped"
        }

    }
}