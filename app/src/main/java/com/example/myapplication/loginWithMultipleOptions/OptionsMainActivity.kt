package com.example.myapplication.loginWithMultipleOptions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityOptionsMainBinding

class OptionsMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityOptionsMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.inflateOnMainFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_emailFragment2_self)
    }
}