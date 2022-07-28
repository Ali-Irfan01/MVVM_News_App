package com.example.myapplication.kotlinFlows.flowOperators

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowOperatorViewModel:ViewModel() {

    var hold = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        while (currentValue > 0)    {
            delay(500L)
            emit(currentValue)
            currentValue--
        }
    }
    init {
        collectFlow()
        //Log.d("TAG", "Emission from flow is completed.")
    }
    private fun collectFlow(){

        //Create a new flow to concatenate its emissions with the previous flow
        val newFlow = flow<Int> {
            var currentValue = 10
            while(currentValue > 0) {
                emit(currentValue)
                currentValue--
            }
        }
        val newFlowTwo = flow{
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main Dish")
            delay(100L)
            emit("Dessert")
        }
        viewModelScope.launch   {
                /*var count = hold
                    .filter {
                        it % 2 === 0
                    }
                    .map {
                        // To Perform any operation on filtered or non-filtered value
                        it * it
                    }
                    .onEach {
                        //
                    }
                    .collect {
                        Log.d("TAG", "Current Value: $it")
                    }
                    .count {
                        Log.d("TAG", "Current Value: $it")
                        it % 2 == 0
                    }
                    .reduce { accumulator, value ->
                        accumulator + value
                    }
                    .
                Log.d("TAG", "Count is: $count")*/
            /*newFlow.flatMapConcat {
                flow {
                    emit(it + 1)
                }
            }.collect{
                Log.d("TAG", "Concatenated data from final flow: $it")
            }*/
            newFlowTwo.onEach {
                Log.d("TAG","$it is delivered in Thread: ${Thread.currentThread().name}")
            }
                //.buffer()
                //.conflate()
                .collectLatest{
                    Log.d("TAG, ","Now Eating $it in Thread: ${Thread.currentThread().name}")
                    delay(1500L)
                    Log.d("TAG", "Finished Eating $it in Thread: ${Thread.currentThread().name}")
                }
        }
    }
}