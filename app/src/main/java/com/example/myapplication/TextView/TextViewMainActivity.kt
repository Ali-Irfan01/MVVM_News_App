package com.example.myapplication.TextView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.EditText.EditTextMainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditTextMainBinding
import com.example.myapplication.databinding.ActivityEditTextMainBinding.inflate
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityMainBinding.inflate

class TextViewMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTextMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnadd.setOnClickListener{
            binding.txtresult.text = (binding.etFirstNum1.text.toString().toInt() + binding.etSecondNum.text.toString().toInt()).toString()
        }
    }
}