package com.vullpes.newsapi.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vullpes.newsapi.data.local.NewsDatabase
import com.vullpes.newsapi.data.remote.NewsAPI
import com.vullpes.newsapi.data.remote.NewsPagingSource
import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val newsDatabase: NewsDatabase
) : NewsRepository
{
    override  fun getLatestNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewsPagingSource(newsAPI) }
        ).flow.map {
            it.map { articleDto -> articleDto.toArticle() }
        }
    }

    override  fun getSavedNewsUsecase(): Flow<List<Article>> {
        return newsDatabase.articleDao().getSavedNews().map {listArticlesDb ->
            listArticlesDb.map { it.toArticle() }
        }
    }

    override  fun searchNewsUsecase(search: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewsPagingSource(newsAPI,search) }
        ).flow.map {
            it.map { articleDto -> articleDto.toArticle() }
        }
    }
}