package com.example.myapplication.CheckBoxandRadioButton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCheckBoxandRadioButtonMainBinding

class CheckBoxandRadioButtonMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBoxandRadioButtonMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBoxandRadioButtonMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOrder.setOnClickListener{
            val selectedRadioButton = binding.rgMeatOptions.checkedRadioButtonId
            val meatOption = findViewById<RadioButton>(selectedRadioButton)
            val cheese = binding.cbCheese.isChecked
            val onions = binding.cbOnions.isChecked
            val salad = binding.cbSalad.isChecked
            val holder = Holder("You have ordered a \n" + "${meatOption.text} Burger with " +
                    (if(cheese) "\nCheese" else "") +
                    (if(onions) "\nOnions" else "") +
                    (if(salad) "\nSalad" else ""))
            //binding.txtfinalOrder.text = orderString

            Toast(this).apply {
                duration = Toast.LENGTH_SHORT
                layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_id))
                //setGravity(Gravity.CENTER_VERTICAL,0,0)
                show()

                Intent(applicationContext, SecondActivity::class.java).also{
                    it.putExtra("ORDER", holder.order)
                    startActivity(it)
                }
            }
        }
    }
}