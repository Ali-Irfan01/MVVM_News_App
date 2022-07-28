package com.example.myapplication.Coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class JobsMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val job = GlobalScope.launch(Dispatchers.Default) {
                Log.d("TAG", "Starting long running calculation...")
               withTimeout(50L) {
                   for (i in 30..40) {
                       //if (isActive)
                           Log.d("TAG", "Result for i = $i: ${fibonacci(i)}")
                   }
               }
            Log.d("TAG", "Ending long running calculation...")
        }

//To manually cancel the job
        /*runBlocking {
            delay(500L)
            job.cancel()
            Log.d("TAG", "Canceled the job!")
        }*/

    }

    fun fibonacci(n: Int): Long{
        return if(n == 0)   0
        else if(n ==1)  1
        else    fibonacci(n-1) + fibonacci(n-2)
    }

}