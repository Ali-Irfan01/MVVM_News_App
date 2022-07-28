package com.example.myapplication.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotificationMainBinding
import com.example.myapplication.databinding.RecyclerViewInflatableLayoutBinding

class NotificationMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationMainBinding

    val CHANNEL_ID = "channelID"
    val CHANNE_NAME = "channel name"
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val intent = Intent(this, NotificationMainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,  PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Awesome Notification")
            .setContentText("This is the content area")
            .setSmallIcon(R.drawable.ic_notification_toast)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        binding.btnNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            val channel = NotificationChannel(CHANNEL_ID, CHANNE_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}