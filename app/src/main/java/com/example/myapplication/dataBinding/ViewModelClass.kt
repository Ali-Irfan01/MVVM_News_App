package com.example.myapplication.dataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelClass: ViewModel() {

    val quoteLiveData = MutableLiveData("What goes around comes around")

    fun updateQuote(){
        quoteLiveData.value = "Wait until you experience it"
    }
}