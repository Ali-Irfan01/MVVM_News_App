package com.example.myapplication.ViewModelScope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityViewModelMainBinding

class ViewModelMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var viewModel = ViewModelProvider(this).get(ViewModelMain::class.java)
        binding.txtResult.text = viewModel.number.toString()

        binding.btnAdd.setOnClickListener {
            viewModel.addNumber()
            binding.txtResult.text = viewModel.number.toString()
        }
    }
}