package com.example.myapplication.daggerHiltRunningApp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.daggerHiltRunningApp.other.Constants.ACTION_PAUSE_SERVICE
import com.example.myapplication.daggerHiltRunningApp.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.myapplication.daggerHiltRunningApp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.myapplication.daggerHiltRunningApp.other.Constants.ACTION_STOP_SERVICE
import com.example.myapplication.daggerHiltRunningApp.other.Constants.FASTEST_LOCATION_INTERVAL
import com.example.myapplication.daggerHiltRunningApp.other.Constants.LOCATION_UPDATE_INTERVAL
import com.example.myapplication.daggerHiltRunningApp.other.Constants.NOTIFICATION_CHANNEL_ID
import com.example.myapplication.daggerHiltRunningApp.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.myapplication.daggerHiltRunningApp.other.Constants.NOTIFICATION_ID
import com.example.myapplication.daggerHiltRunningApp.other.Constants.TIMER_UPDATE_INTERVAL
import com.example.myapplication.daggerHiltRunningApp.other.TrackingUtility
import com.example.myapplication.daggerHiltRunningApp.ui.RunningAppMainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import timber.log.Timber
import javax.inject.Inject


typealias Polyline = MutableList<LatLng>
typealias PolyLines = MutableList<Polyline>
// to make MutableLiveData<MutableList<MutableList<LatLng>>> => MutableLiveData<PolyLines>

@AndroidEntryPoint
class TrackingService: LifecycleService() {

    var isFirstRun = true
    var serviceKilled = false

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val timeRunInSeconds = MutableLiveData<Long>()

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    lateinit var currentNotificationBuilder: NotificationCompat.Builder

    companion object    {
        val timeRunInMillis = MutableLiveData<Long>()
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<PolyLines>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRunInSeconds.postValue(0L)
        timeRunInMillis.postValue(0L)
    }

    override fun onCreate() {
        super.onCreate()
        currentNotificationBuilder = baseNotificationBuilder
        postInitialValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this) {
            updateLocationTracking(it)
            updateNotificationTrackingServiceState(it)
        }
    }

    private fun killService(){
        serviceKilled = true
        isFirstRun = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

    // This function will be called when we send an intent to this service it'll then check for intent.action
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {//if that intent is not equal to null
            when(it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun)  {
                        startForegroundService()
                        isFirstRun = false
                    }   else    {
                        Timber.d("Resuming service...")
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused Service")
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped Service")
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimeEnabled = false
    private var lapTime = 0L
    private var timeRun = 0L
    private var timeStarted = 0L
    private var lastSecondTimeStamp = 0L


    private fun startTimer()   {
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimeEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!)  {
                // time difference between now and timeStarted
                lapTime = System.currentTimeMillis() - timeStarted
                // post the new lapTime
                timeRunInMillis.postValue(timeRun + lapTime)
                if(timeRunInMillis.value!! >= lastSecondTimeStamp + 1000L)  {
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)
                    lastSecondTimeStamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

    private fun pauseService()  {
        isTracking.postValue(false)
        isTimeEnabled = false
    }

    private fun updateNotificationTrackingServiceState(isTracking: Boolean) {
        val notificationActionText = if(isTracking) "Pause" else "Resume"
        val pendingIntent = if(isTracking){
            val pauseIntent = Intent(this, TrackingService::class.java)
                .apply {
                    action = ACTION_PAUSE_SERVICE
                }
            PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
        }   else    {
            val resumeIntent = Intent(this, TrackingService::class.java)
                .apply {
                    action = ACTION_START_OR_RESUME_SERVICE
                }
            PendingIntent.getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // The way to remove all actions before we update that notification with the new action
        currentNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(currentNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }
        if(!serviceKilled) {
            currentNotificationBuilder = baseNotificationBuilder
                .addAction(R.drawable.ic_pause_black_24dp, notificationActionText, pendingIntent)
            notificationManager.notify(NOTIFICATION_ID, currentNotificationBuilder.build())
        }
    }

    private fun updateLocationTracking(isTracking: Boolean) {
        if(isTracking)  {
            if(TrackingUtility.hasLocationPermissions(this) >= 0)    {
                val request = com.google.android.gms.location.LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = Priority.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }   else    {
            Timber.d("Permission denied in while updating the app")
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    val locationCallback = object : LocationCallback()  {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if(isTracking.value!!)  {
                result?.locations?.let { locations ->
                    for(location in locations)  {
                        addPathPoint(location)
                        Timber.d("NEW LOCATION ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    //Creates new notification channel by calling fun for devices running above O and creates a new notification, and start in foreground
    private fun startForegroundService()    {
        startTimer()
        isTracking.postValue(true)

        val notificationManager  = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            createNotificationChannel(notificationManager)
        }

        /**
         * Injected this code
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running App")
            .setContentText("00:00:00")  //will be updated later on based on time
            .setContentIntent(getMainActivityPendingIntent()) */

        startForeground(NOTIFICATION_ID, /*notificationBuilder*/baseNotificationBuilder.build())

        timeRunInSeconds.observe(this, Observer {
            if(!serviceKilled) {
                val notification = currentNotificationBuilder
                    .setContentText(TrackingUtility.getFormattedStopWatchTime(it * 1000))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        })
    }

    /**
     * Injected this code too
    // Created a new intent
    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, RunningAppMainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT // Whenever we launch that pending intent and it already exists it will update it instead of recreating / restarting it
    )   */

    // Function to create notification channel
    @RequiresApi(Build.VERSION_CODES.O) //Channel can only be created above Android Oreo
    private fun  createNotificationChannel(notificationManager: NotificationManager)    {
        //  We have to give notification channel id, name, and its importance IMPORTANCE_LOW does not make the device ring above it the device rings
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}