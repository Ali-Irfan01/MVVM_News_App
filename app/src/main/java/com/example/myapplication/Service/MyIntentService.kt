package com.example.myapplication.Service

import android.app.IntentService
import android.content.Intent
import android.content.IntentSender
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {
    init {
        instance = this
    }
    companion object{
        private lateinit var instance: MyIntentService

        var isRunnig = false

        fun stopService(){
            Log.v("TAG", "Service is stopping...")
            isRunnig = false
            instance.stopSelf()
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunnig = true
            while (isRunnig)    {
                Log.v("TAG","Service is running...")
                Thread.sleep(1000)
            }
        }catch (e: InterruptedException){
            Thread.currentThread().interrupt()
        }
    }
}