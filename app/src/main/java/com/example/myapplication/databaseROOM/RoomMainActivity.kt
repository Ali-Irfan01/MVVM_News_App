package com.example.myapplication.databaseROOM

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.R

class RoomMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_main)

        // To set labels of fragments at top nav bar
        //setupActionBarWithNavController(findNavController(R.id.fragmentContainerViewRoom))
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewRoom) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }
    //To Implement back arrow click, set navigation by getting navigation controller and setting the onSupportNavigateUp()
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewRoom) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}