package com.example.myapplication.rssNewsReader.model

data class GeneralNewsModel(
    val title: String?,
    val link: String?,
    var description: String?,
    var content: String?,
    var imgUrl: String?,
    var pubDate: String?
)

