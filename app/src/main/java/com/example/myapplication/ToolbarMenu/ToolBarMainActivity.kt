package com.example.myapplication.ToolbarMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityToolBarMainBinding

class ToolBarMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToolBarMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolBarMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miAddContact -> Toast.makeText(this, "Add Contact Clicked", Toast.LENGTH_SHORT).show()
            R.id.miFavorite -> Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
            R.id.miSettings -> Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
            R.id.miClose -> finish()
            R.id.miFeedback -> Toast.makeText(this, "Feedback Clicked", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}