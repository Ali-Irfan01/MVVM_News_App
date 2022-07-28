package com.example.myapplication.dataBinding.bindingAdaptersTwo

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load


@BindingAdapter("loadImageFromUrl", "displayTitle")
fun ImageView.loadImageFromUrl(profilePhoto: String, title: String){
    this.load(profilePhoto)
    Log.d("TAG", "Title is $title")
}

@BindingAdapter("setVisibility")
fun View.setMyViewVisibility(points: Int){
    if(points>10){
        this.visibility = View.INVISIBLE
    }

/*    when(this){
        is TextView ->  {}
        is ImageView -> {}
        is EditText -> {}
    }*/

}