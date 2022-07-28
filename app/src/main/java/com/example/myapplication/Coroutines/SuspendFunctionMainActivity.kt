package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuspendFunctionMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspend_function_main)

        Log.d("TAG", "I am in Main Thread")

        GlobalScope.launch {
            val networkCallAnswer = doNetworkCall()
            val networkCallAnswer2 = doNetworkCall()
            Log.d("TAG", networkCallAnswer)
            Log.d("TAG", networkCallAnswer2)
        }

    }

    suspend fun doNetworkCall() : String    {
        delay(3000L)
        return "This is the Answer"
    }

}