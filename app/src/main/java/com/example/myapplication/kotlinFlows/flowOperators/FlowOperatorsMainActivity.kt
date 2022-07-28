package com.example.myapplication.kotlinFlows.flowOperators

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFlowOperatorsMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowOperatorsMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlowOperatorsMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowOperatorsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flowOperatorViewModelBinder = ViewModelProvider(this)[FlowOperatorViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {

            flowOperatorViewModelBinder.hold.collect{

                withContext(Dispatchers.Main){

                    binding.txtFlowOperators.text = it.toString()
                }
            }
        }

    }
}