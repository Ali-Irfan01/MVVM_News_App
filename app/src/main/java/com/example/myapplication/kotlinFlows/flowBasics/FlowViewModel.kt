package com.example.myapplication.kotlinFlows.flowBasics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {


    val countDownFlow = flow<Int> {
        var startingValue = 10
        var currentValue = startingValue
        emit(currentValue)

        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow.collectLatest {  count ->
                /*delay(2000L)*/
                Log.d("TAG","Current count is: $count")
            }
        }
    }

}