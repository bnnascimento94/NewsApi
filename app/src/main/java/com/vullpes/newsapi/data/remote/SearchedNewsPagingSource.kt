package com.vullpes.newsapi.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vullpes.newsapi.data.remote.dto.ArticleDto

class SearchedNewsPagingSource(
    private val remoteDataSource: NewsAPI,
    private val query:String? = null
): PagingSource<Int, ArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val position = params.key ?: 1
            val response = remoteDataSource.getAllNewsAboutTopic(
                page = position,
                pageSize = params.loadSize,
                q = query
            )

            if(response.isSuccessful && response.body() != null){
                val articles = response.body()?.articles ?: emptyList()
                val  i= if (articles.isEmpty()) null else (position + 1)


                LoadResult.Page(
                    data = articles,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = i
                )

            }else{
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}