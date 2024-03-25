package com.vullpes.newsapi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface ArticleDao {

    @Insert
    suspend fun saveArticleDb(articleDb: ArticleDb)

    @Delete
    suspend fun deleteArticleDb(articleDb: ArticleDb)

}