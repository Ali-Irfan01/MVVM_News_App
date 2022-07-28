package com.example.myapplication.SlidableMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySlidableMenuNavigationDrawerMainBinding

class SlidableMenuNavigationDrawerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySlidableMenuNavigationDrawerMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlidableMenuNavigationDrawerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //for back arraow click on close Support
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navView.setNavigationItemSelectedListener{

                if(it.itemId == R.id.miItem1)
                    it.icon = getDrawable(R.drawable.ic_profile)
                if(it.itemId == R.id.miItem2)
                    it.icon = getDrawable(R.drawable.ic_message_selected)
                if(it.itemId == R.id.miItem3)
                    it.icon = getDrawable(R.drawable.ic_settings_selected)

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}