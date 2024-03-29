package com.vullpes.newsapi.di

import android.content.Context
import androidx.room.Room
import com.vullpes.newsapi.FakeNewsApiRepository
import com.vullpes.newsapi.data.NewsRepositoryImpl
import com.vullpes.newsapi.data.local.NewsDatabase
import com.vullpes.newsapi.data.remote.NewsAPI
import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.usecases.DeleteArticleUseCase
import com.vullpes.newsapi.domain.usecases.GetLatestNewsUsecase
import com.vullpes.newsapi.domain.usecases.GetSavedNewsUsecase
import com.vullpes.newsapi.domain.usecases.SaveNewsUserCase
import com.vullpes.newsapi.domain.usecases.SearchNewsUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApplicationModule::class]
)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): NewsDatabase {
        return  Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesNewsRepository() : NewsRepository = FakeNewsApiRepository()


    @Singleton
    @Provides
    fun providesGetLatestNewsUsecase(
        newsRepository: NewsRepository
    ): GetLatestNewsUsecase = GetLatestNewsUsecase(newsRepository)

    @Singleton
    @Provides
    fun providesGetSavedNewsUsecase(
        newsRepository: NewsRepository
    ): GetSavedNewsUsecase = GetSavedNewsUsecase(newsRepository)

    @Singleton
    @Provides
    fun providesSaveNewsUsecase(
        newsRepository: NewsRepository
    ): SaveNewsUserCase = SaveNewsUserCase(newsRepository)

    @Singleton
    @Provides
    fun providesSearchNewsUsecase(
        newsRepository: NewsRepository
    ): SearchNewsUsecase = SearchNewsUsecase(newsRepository)

    @Singleton
    @Provides
    fun providesDeleteArticleUsecase(
        newsRepository: NewsRepository
    ): DeleteArticleUseCase = DeleteArticleUseCase(newsRepository)


}