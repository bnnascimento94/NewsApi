package com.vullpes.newsapi.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vullpes.newsapi.data.remote.dto.ArticleDto


class NewsPagingSource(
    private val remoteDataSource: NewsAPI
): PagingSource<Int, ArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val position = params.key ?: 1
            val response = remoteDataSource.getNews(
                country = "br",
                page = position,
                pageSize = params.loadSize
            )
            print("pagesize ${params.loadSize.toString()}")


            if(response.body() != null && response.isSuccessful){
                val articles = response.body()?.articles ?: emptyList()

                LoadResult.Page(
                    data = articles,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (articles.isEmpty()) null else (position + 1)
                )

            }else{
                LoadResult.Error(Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(Exception("No Response"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}