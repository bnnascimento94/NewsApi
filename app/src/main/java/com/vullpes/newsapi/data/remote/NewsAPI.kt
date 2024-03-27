package com.vullpes.newsapi.data.remote

import com.vullpes.newsapi.BuildConfig
import com.vullpes.newsapi.data.remote.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("country") country:String = "br",
                        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
                        @Query("pageSize") pageSize: Int = 10,
                        @Query("page") page: Int,
                        @Query("q") q:String? = null): Response<NewsDto>


    @GET("v2/everything")
    suspend fun getAllNewsAboutTopic(
                        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
                        @Query("pageSize") pageSize: Int = 10,
                        @Query("page") page: Int,
                        @Query("q") q:String? = null): Response<NewsDto>

}