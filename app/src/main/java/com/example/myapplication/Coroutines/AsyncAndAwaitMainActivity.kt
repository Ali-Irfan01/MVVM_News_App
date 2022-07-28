package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class AsyncAndAwaitMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val answer1 = async { networkCall() }
                val answer2 = async { networkCall() }
                Log.d("TAG", "Answer is ${answer1.await()}")
                Log.d("TAG", "Answer is ${answer2.await()}")
            }
            Log.d("TAG", "Request took $time ms")
        }
    }
    suspend fun networkCall(): String{
        delay(3000L)
        return "Answer 1"
    }

}