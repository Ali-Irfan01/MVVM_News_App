package com.example.myapplication.ImageView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityImageViewMainBinding

class ImageViewMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnaddimage.setOnClickListener{
            binding.imageView.setImageResource(R.drawable.testimage)
        }

    }
}