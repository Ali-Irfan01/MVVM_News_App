package com.example.myapplication.Fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFragmentMainBinding

class FragmentMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val FirstFragment = FirstFragment()
        val SecondFragment = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentOne, FirstFragment)
            commit()
        }

        binding.btnFragment1.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentOne, FirstFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnFragment2.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentOne, SecondFragment)
                addToBackStack(null)
                commit()
            }
        }

    }
}