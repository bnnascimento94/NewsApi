package com.vullpes.newsapi.data.remote

import com.vullpes.newsapi.data.remote.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNews(country:String, apiKey: String, pageSize: Int = 20, page: Int, q:String? = null): Response<NewsDto>

}