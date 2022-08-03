package com.example.myapplication.mVVMNewsApp.repository

import com.example.myapplication.mVVMNewsApp.api.RetrofitInstance
import com.example.myapplication.mVVMNewsApp.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

}