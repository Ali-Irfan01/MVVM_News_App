package com.example.myapplication.kotlinFlows.stateAndSharedFlows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStateAndSharedFlowMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StateAndSharedFlowMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateAndSharedFlowMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateAndSharedFlowMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateAndSharedFlowViewModel =
            ViewModelProvider(this)[SharedAndStateFlowViewModel::class.java]


        collectLatestLifecycleFlow(stateAndSharedFlowViewModel.stateFlow) {
            binding.txtStateAndSharedFlow.text = it.toString()
        }

        binding.btnStateAndSharedFlow.setOnClickListener {
            stateAndSharedFlowViewModel.incrementCounter()
        }
    }
}

fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}