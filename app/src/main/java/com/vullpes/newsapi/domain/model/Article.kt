package com.vullpes.newsapi.domain.model

data class Article(
    val articleId:Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)