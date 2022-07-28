package com.example.myapplication.ImplicitIntents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityGalleryMainBinding

class GalleryMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhoto.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 0){
            val uri = data?.data
            binding.ivPhoto.setImageURI(uri)
        }
    }
}