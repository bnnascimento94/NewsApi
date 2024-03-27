package com.vullpes.newsapi.domain.usecases

import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.model.Article
import javax.inject.Inject

class SaveNewsUserCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(article: Article) = newsRepository.insertArticle(article)
}