package com.example.myapplication.dataBinding.bindingAdapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBindingAdaptersMainBinding

class BindingAdaptersMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindingAdaptersMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindingAdaptersMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postInstance = Post("Binding Adapter", "This is my first biding adapter example code in kotlin",
        "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80")

        binding.post = postInstance
    }
}