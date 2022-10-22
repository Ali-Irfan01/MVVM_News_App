package com.example.myapplication.daggerHiltRunningApp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.daggerHiltRunningApp.ui.RunningAppMainActivity

class MyDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Grant Permission")
            builder.setMessage("Please grant background location permission, so we can accurately measure your runs")
                .setPositiveButton("Grant Permission",
                    DialogInterface.OnClickListener { dialog, id ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", (activity as RunningAppMainActivity).packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    })
                .setNegativeButton("I am okay with limited features",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText((activity as RunningAppMainActivity), "Please grant location permissions from settings", Toast.LENGTH_SHORT).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}