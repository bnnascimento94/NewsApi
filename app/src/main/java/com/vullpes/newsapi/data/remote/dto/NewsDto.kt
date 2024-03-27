package com.vullpes.newsapi.data.remote.dto

data class NewsDto(
    val articles: List<ArticleDto>? = null,
    val status: String,
    val totalResults: Int
)