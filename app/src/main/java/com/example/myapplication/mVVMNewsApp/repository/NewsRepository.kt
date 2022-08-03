package com.example.myapplication.mVVMNewsApp.repository

import com.example.myapplication.mVVMNewsApp.api.RetrofitInstance
import com.example.myapplication.mVVMNewsApp.db.ArticleDatabase
import com.example.myapplication.mVVMNewsApp.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    // For ROOM DataBase

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}