package com.vullpes.newsapi.domain.usecases

import com.vullpes.newsapi.domain.NewsRepository
import javax.inject.Inject

class GetLatestNewsUsecase @Inject constructor(private var newsRepository: NewsRepository) {

    operator fun invoke() = newsRepository.getLatestNews()
}