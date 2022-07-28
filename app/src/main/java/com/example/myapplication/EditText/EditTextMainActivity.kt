package com.example.myapplication.EditText

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class EditTextMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnApply.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val birthDate = binding.etBirthDate.text.toString()
            val country = binding.etCountry.text.toString()
            Log.v("TAG", "$firstName $lastName, born on $birthDate, from $country pressed button")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.v("TAG", "I am Paused!!")
    }

    override fun onResume() {
        super.onResume()
        Log.v("TAG", "I am Resumed!!")
    }
}