package com.vullpes.newsapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("news_db")
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    val articleId:Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
