package com.vullpes.newsapi.domain

import androidx.paging.PagingData
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getLatestNews(): Flow<PagingData<Article>>
    fun getSavedNewsUsecase(): Flow<List<Article>>
    fun searchNewsUsecase(search:String):Flow<PagingData<Article>>

}