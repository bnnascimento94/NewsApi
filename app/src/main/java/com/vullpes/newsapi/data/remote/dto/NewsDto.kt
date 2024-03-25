package com.vullpes.newsapi.data.remote.dto

data class NewsDto(
    val articleDtos: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)