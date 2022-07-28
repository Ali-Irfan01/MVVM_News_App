package com.example.myapplication.SharedPreference

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySharedPreferenceMainBinding

class SharedPreferenceMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySharedPreferenceMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferenceMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.btnSave.setOnClickListener{
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val isAdult = binding.spCheckBox.isChecked

            editor.apply{
                putString("name", name)
                putInt("age", age)
                putBoolean("isAdult", isAdult)
                apply()
            }
            binding.etName.setText("")
            binding.etAge.setText("")
            binding.spCheckBox.isChecked = false
        }

        binding.btnLoad.setOnClickListener {
            val name = sharedPref.getString("name", "Not Fetched")
            val age = sharedPref.getInt("age", 0)
            val isAdult = sharedPref.getBoolean("isAdult", false)

            binding.etName.setText(name)
            binding.etAge.setText(age.toString())
            binding.spCheckBox.isChecked = isAdult
        }

    }
}