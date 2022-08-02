package com.example.myapplication.mVVMNewsApp.models

import com.example.myapplication.mVVMNewsApp.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)