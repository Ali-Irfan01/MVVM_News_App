package com.example.myapplication.TabLayoutViewPager

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTabLayoutViewPagerMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutViewPagerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabLayoutViewPagerMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutViewPagerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = listOf(
            R.drawable.testimage,
            R.drawable.view_pager_image1,
            R.drawable.view_pager_image2,
            R.drawable.view_pager_image3,
            R.drawable.view_pager_image4
        )

        val adapter = TabViewPagerAdapter(images)
        binding.tabViewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.tabViewPager)  {   tab, position ->
            tab.text = "Tab ${position + 1}"
            tab.icon = getDrawable(R.drawable.ic_tab_view)
        }.attach()


        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.icon = getDrawable(R.drawable.ic_tab_view_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon = getDrawable(R.drawable.ic_tab_view)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    binding.btnrightTab.setOnClickListener{
        binding.tabViewPager.beginFakeDrag()
        binding.tabViewPager.fakeDragBy(-10f)
        binding.tabViewPager.endFakeDrag()
    }
    binding.btnSwipeLeft.setOnClickListener{
        binding.tabViewPager.beginFakeDrag()
        binding.tabViewPager.fakeDragBy(10f)
        binding.tabViewPager.endFakeDrag()
    }
    }
}