package com.example.myapplication.kotlinFlows.flowBasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityCountdownFlowMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountdownFlowMainActivity : ComponentActivity() {
    private lateinit var binding: ActivityCountdownFlowMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountdownFlowMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var myViewModel = ViewModelProvider(this)[FlowViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {

            var count = myViewModel.countDownFlow.collect{

                withContext(Dispatchers.Main) {

                    binding.txtflowBasic.text = it.toString()
                    Log.d("TAG", "Current count is: ${it.toString()}")
                }
            }
        }
    }
}