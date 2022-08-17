package com.example.myapplication.daggerHiltRunningApp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication: Application() {
}