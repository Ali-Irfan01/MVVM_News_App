package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RunBlockingMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAG", "Before Run Blocking")

        runBlocking {

            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d("TAG", "Finished IO Coroutine 1")
            }
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d("TAG", "Finished IO Coroutine 2")
            }
            Log.d("TAG", "Start of Run Blocking")
            delay(3100L)
            Log.d("TAG", "End Run BLocking")

        }
        Log.d("TAG", "After Run BLocking")
    }
}