package com.vullpes.newsapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleDb::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}