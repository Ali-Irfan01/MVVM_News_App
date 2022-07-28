package com.example.myapplication.dataBinding.bindingAdaptersTwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBindingAdapterTwoMainBinding

class BindingAdapterTwoMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBindingAdapterTwoMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindingAdapterTwoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = DataModel("Ali",
            "Android Developer",
            "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80",
            9)

            binding.sample = data
    }
}