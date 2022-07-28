package com.example.myapplication.ViewPager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityViewPagerMainBinding

class ViewPagerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = listOf(
            R.drawable.testimage,
            R.drawable.view_pager_image1,
            R.drawable.view_pager_image2,
            R.drawable.view_pager_image3,
            R.drawable.view_pager_image4
        )

        val adapter = ViewPagerAdapter(images)
        binding.viewPager.adapter = adapter

        binding.btnDragRight.setOnClickListener{
            binding.viewPager.beginFakeDrag()
            binding.viewPager.fakeDragBy(-30f)
            binding.viewPager.endFakeDrag()
        }

        binding.btnDragLeft.setOnClickListener{
            binding.viewPager.beginFakeDrag()
            binding.viewPager.fakeDragBy(30f)
            binding.viewPager.endFakeDrag()
        }
    }
}