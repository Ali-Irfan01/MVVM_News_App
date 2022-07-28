package com.example.myapplication.Spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySpinnerMainBinding
import com.google.android.material.navigation.NavigationBarView

class SpinnerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpinnerMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val customList = listOf("First", "Second", "Third", "Fourth")
        //val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, customList)
        //binding.spMonths.adapter = adapter



        binding.spMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Toast.makeText(
                    this@SpinnerMainActivity,
                    "You selected ${parent?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }
}