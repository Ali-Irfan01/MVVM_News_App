package com.example.myapplication.daggerHiltRunningApp.other

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.daggerHiltRunningApp.services.Polyline
import timber.log.Timber
import java.util.concurrent.TimeUnit

object TrackingUtility {
    fun hasLocationFinePermission(ctx: Context) =
        ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    fun hasLocationForegroundPermission(ctx: Context) =
        ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasLocationBackgroundPermission(ctx: Context) =
        ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    @RequiresApi(Build.VERSION_CODES.M)
    fun hasBatteryOptimizationPermission(ctx: Context) =
        ActivityCompat.checkSelfPermission(ctx,Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasLocationPermissions(ctx: Context?): Int {
        val FINE_LOCATION: Int =
            ContextCompat.checkSelfPermission(ctx!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val COARSE_LOCATION: Int =
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
        val BACKGROUND_LOCATION: Int =
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_BACKGROUND_LOCATION)


        Timber.tag("PERMISSION_TAG")
            .d("Fine Location: $FINE_LOCATION, Coarse Location: $COARSE_LOCATION, Background Location: $BACKGROUND_LOCATION")
        return FINE_LOCATION + COARSE_LOCATION + BACKGROUND_LOCATION
    }

    fun calculatePolylineLength(polyline: Polyline): Float   {
        var distance = 0f
        for(i in 0..polyline.size - 2)  {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]
            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
                )
            distance += result[0]
        }
        return distance
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String    {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if(!includeMillis)  {
            return "${if(hours < 10) "0" else ""}$hours:" +
                    "${if(minutes < 10) "0" else ""}$minutes:" +
                    "${if(seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10) "0" else ""}$minutes:" +
                "${if(seconds < 10) "0" else ""}$seconds:" +
                "${if(milliseconds < 10) "0" else ""}$milliseconds"
    }

}