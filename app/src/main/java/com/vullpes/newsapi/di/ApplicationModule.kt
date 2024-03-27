package com.vullpes.newsapi.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vullpes.newsapi.R
import com.vullpes.newsapi.data.NewsRepositoryImpl
import com.vullpes.newsapi.data.local.NewsDatabase
import com.vullpes.newsapi.data.remote.NewsAPI
import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.usecases.DeleteArticleUseCase
import com.vullpes.newsapi.domain.usecases.GetLatestNewsUsecase
import com.vullpes.newsapi.domain.usecases.GetSavedNewsUsecase
import com.vullpes.newsapi.domain.usecases.SaveNewsUserCase
import com.vullpes.newsapi.domain.usecases.SearchNewsUsecase
import com.vullpes.newsapi.util.Constants.BASE_URL
import com.vullpes.newsapi.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providesNewsApi(): NewsAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun providesNewsRepository(
        newsAPI: NewsAPI, newsDatabase: NewsDatabase
    ) : NewsRepository= NewsRepositoryImpl(newsAPI, newsDatabase)

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