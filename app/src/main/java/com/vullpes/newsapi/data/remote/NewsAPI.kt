package com.vullpes.newsapi.data.remote

import com.vullpes.newsapi.BuildConfig
import com.vullpes.newsapi.data.remote.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("country") country:String,
                        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
                        @Query("pageSize") pageSize: Int = 20,
                        @Query("page") page: Int,
                        @Query("q") q:String? = null): Response<NewsDto>

}