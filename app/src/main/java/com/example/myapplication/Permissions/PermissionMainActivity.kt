package com.example.myapplication.Permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPermissionMainBinding

class PermissionMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestPermission.setOnClickListener{
            requestPermission()
        }
    }
    private fun hasWriteExternalPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun requestPermission() {
        var permissions = mutableListOf<String>()
        if(!hasWriteExternalPermission()){
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasLocationForegroundPermission()){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!hasLocationBackgroundPermission() && hasLocationForegroundPermission()){
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if(permissions.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED)    {
                    Log.v("TAG", "${permissions[i]} granted")
                }
            }
        }
    }
}