package com.vullpes.newsapi.domain

import androidx.paging.PagingData
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getLatestNews(): Flow<PagingData<Article>>
    fun getSavedNews(): Flow<List<Article>>
    fun searchNews(search:String):Flow<PagingData<Article>>
    suspend fun insertArticle(article: Article)
    suspend fun deleteArticle(article: Article)

}