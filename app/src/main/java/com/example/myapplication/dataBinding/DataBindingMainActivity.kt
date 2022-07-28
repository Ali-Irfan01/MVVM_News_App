package com.example.myapplication.dataBinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityDataBindingMainBinding

class DataBindingMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataBindingMainBinding
    private lateinit var myViewModelInstance: ViewModelClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataBindingMainBinding.inflate(layoutInflater)
        myViewModelInstance = ViewModelProvider(this)[ViewModelClass::class.java]
        setContentView(binding.root)


        binding.mainViewModel = myViewModelInstance
        binding.lifecycleOwner = this
    /*binding.btnChangeInput.setOnClickListener {
            myViewModelInstance.updateQuote()
        }*/
    }
}