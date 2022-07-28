package com.example.myapplication.Coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelAndLifeCycleScope : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var viewModel = ViewModelProvider(this).get(ViewModelMainActivity::class.java)

        lifecycleScope.launch{
            var viewModel = ViewModelProvider(this@ViewModelAndLifeCycleScope).get(ViewModelMainActivity::class.java)
            Log.d("TAG", "LifeCycle Scope Thread is Created!!")
            delay(2000L)
            Intent(this@ViewModelAndLifeCycleScope, SampleActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }
    override fun onDestroy() {
        Log.d("TAG", "LifeCycle coroutine: ${Thread.currentThread().name} is Destroyed")
        super.onDestroy()
    }
}