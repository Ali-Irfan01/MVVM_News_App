package com.example.myapplication.kotlinFlows.stateAndSharedFlows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedAndStateFlowViewModel:ViewModel() {


    var hold = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        while(currentValue >= 0){
            delay(500L)
            emit(currentValue)
            currentValue--
        }

    }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun incrementCounter(){
        _stateFlow.value += 1
    }


    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    init {
        //collectFlow()
        viewModelScope.launch {
            sharedFlow.collect{
                delay(2000L)
                Log.d("TAG", "Received Number is $it ")
            }
        }
        viewModelScope.launch {
            sharedFlow.collect{
                delay(3000L)
                Log.d("TAG", "Received number is $it")
            }
        }
        squareNumber(3)
    }


    fun squareNumber(number: Int)    {
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }

    private fun collectFlow(){

        val flow  = flow<String> {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main Dish")
            delay(100L)
            emit("Dessert")
        }
        viewModelScope.launch {
            flow.onEach {
                Log.d("TAG", "FLOW: $it is Delivered")
            }
                .collect {
                    Log.d("TAG","FLOW: now Eating $it")
                    delay(1500L)
                    Log.d("TAG", "FLOW: finished Eating $it")
                }
        }

    }


}