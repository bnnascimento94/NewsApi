package com.vullpes.newsapi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert
    suspend fun saveArticleDb(articleDb: ArticleDb)

    @Delete
    suspend fun deleteArticleDb(articleDb: ArticleDb)

    @Query("select * from news_db")
    fun getSavedNews(): Flow<List<ArticleDb>>


}