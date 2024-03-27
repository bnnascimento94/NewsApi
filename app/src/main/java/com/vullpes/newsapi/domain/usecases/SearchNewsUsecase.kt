package com.vullpes.newsapi.domain.usecases

import com.vullpes.newsapi.domain.NewsRepository
import javax.inject.Inject

class SearchNewsUsecase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(search: String) = newsRepository.searchNews(search)
}