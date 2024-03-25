package com.vullpes.newsapi.data.remote.dto

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)