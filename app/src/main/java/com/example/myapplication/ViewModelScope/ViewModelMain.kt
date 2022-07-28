package com.example.myapplication.ViewModelScope

import androidx.lifecycle.ViewModel

class ViewModelMain: ViewModel() {

    var number = 0
    fun addNumber(){
        number++
    }
}