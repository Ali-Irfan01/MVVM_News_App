package com.example.myapplication.BottomNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.Fragment.BlankFragment
import com.example.myapplication.Fragment.FirstFragment
import com.example.myapplication.Fragment.SecondFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBottomNavigationMainBinding

class BottomNavigationMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = BlankFragment()

        binding.bottomNavigations.setOnItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miMessages -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentOne, fragment)
            commit()
        }
}