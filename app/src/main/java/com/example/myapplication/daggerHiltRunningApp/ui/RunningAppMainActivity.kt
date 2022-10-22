package com.example.myapplication.daggerHiltRunningApp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.daggerHiltRunningApp.other.Constants
import com.example.myapplication.daggerHiltRunningApp.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.myapplication.daggerHiltRunningApp.ui.fragments.MyDialog
import com.example.myapplication.databinding.ActivityRunningAppMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunningAppMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityRunningAppMainBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningAppMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // If our main activity was destroyed
        navigateToTrackingFragmentIfNeeded(intent)

        /**
         * setSupportActionBar(binding.toolbar) Do not use this SHIT
         * binding.bottomNavigationView.setupWithNavController(binding.navHostFragment.findNavController())
         * Nav Host fragment can only be set using these 2 lines in kotlin  */

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            /** NO-OP */
        }

        navController.addOnDestinationChangedListener{ _, destination, _ ->
                when(destination.id)    {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    else ->
                        binding.bottomNavigationView.visibility = View.GONE
                }
            }
    }

    //If activity was not destroyed, this new intent function will be called
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    // Whenever the permissions are asked in fragment onRequestPermissionResult is called from the host activity, so we handle permission grant or deny here
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var count = 0
        if (requestCode == Constants.REQUEST_CODE_LOCATION_PERMISSION && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "${permissions[i]} granted")
                }
                if (grantResults[i] == PackageManager.PERMISSION_DENIED && count == 0) {
                    count++
                    //myDialog.show(supportFragmentManager, "dialog")
                }
            }
            if(count > 0)
                MyDialog().show(supportFragmentManager, "dialog")
        }
    }


    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        // if action is ACTION_SHOW_TRACKING_FRAGMENT it means that intent is launched by notification click
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_global_trackingFragment)
        }
    }
}