package com.example.myapplication.Coroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelMainActivity: ViewModel() {
    init {
        viewModelScope.launch {
            Log.d("TAG", "View Model Scope thread is Created!!")
            while (true) {
                delay(500L)
                Log.d(
                    "TAG",
                    "View model coroutine: ${Thread.currentThread().name} is still running..."
                )
            }
        }
    }
    override fun onCleared() {
        Log.d("TAG", "View Model coroutine: ${Thread.currentThread().name} is destroyed")
        super.onCleared()
    }
}