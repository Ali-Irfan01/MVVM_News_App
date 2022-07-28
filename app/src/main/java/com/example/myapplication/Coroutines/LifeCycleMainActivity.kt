package com.example.myapplication.Coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLifeCycleMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LifeCycleMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLifeCycleMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifeCycleMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartActivity.setOnClickListener {

            lifecycleScope.launch {
                while(true){
                    delay(1000L)
                    Log.d("TAG", "Thread: ${Thread.currentThread().name} of First Activity is still running...")
                }
            }
            GlobalScope.launch {
                delay(5000L)
                Intent(this@LifeCycleMainActivity, LifecycleSecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}