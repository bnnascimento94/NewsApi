package com.vullpes.newsapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vullpes.newsapi.data.remote.dto.Source

@Entity("news_db")
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    val articleId:Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
