package com.vullpes.newsapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("news_db")
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    val articleId:Int = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)
