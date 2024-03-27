package com.vullpes.newsapi.di

import android.content.Context
import androidx.room.Room
import com.vullpes.newsapi.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): NewsDatabase {
        return  Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).allowMainThreadQueries().build()
    }

}