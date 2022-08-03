package com.example.myapplication.mVVMNewsApp.models

import com.example.myapplication.mVVMNewsApp.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)