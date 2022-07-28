package com.example.myapplication.dataBinding.viewModelAndLiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelVariable: ViewModel() {

    val message  = MutableLiveData<String>()


    fun setMessage(text: String)    {
        message.value = text
    }
}