package com.example.myapplication.dataBinding.viewModelAndLiveData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityVariableBindingMainBinding

class VariableBindingMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVariableBindingMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVariableBindingMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var viewModelInstance = ViewModelProvider(this)[ViewModelVariable::class.java]


        //Set View Model data Variable to viewModel Instance initialized in Main Activity
        binding.viewModelVariable  = viewModelInstance
        //Set lifecycleOwner context to this if no observer is implemented for liveData instance in ViewModel Class
        binding.lifecycleOwner = this
        /*viewModelInstance.message.observe(this) {
            binding.txtVariableBinding.text = it
        }
        No need to implement observer when we have declared view model instance directly in XML*/

        binding.edtVariableBinding.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelInstance.setMessage(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}