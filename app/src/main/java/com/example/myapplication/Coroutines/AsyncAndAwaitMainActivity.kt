package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class AsyncAndAwaitMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val answer1 = async { networkCall() }
                val answer2 = async { networkCall() }
                Timber.tag("TAG").d("Answer is %s", answer1.await())
                Timber.tag("TAG").d("Answer cis %s", answer2.await())
            }
            Log.d("TAG", "Request took $time ms")
        }
    }
    suspend fun networkCall(): String{
        delay(3000L)
        return "Answer 1"
    }

}