package com.vullpes.newsapi.domain.usecases

import androidx.paging.filter
import com.vullpes.newsapi.domain.NewsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLatestNewsUsecase @Inject constructor(private var newsRepository: NewsRepository) {

    fun execute() = newsRepository.getLatestNews().map {
        it.filter { article -> !article.title.contentEquals("[Removed]") &&
                    !article.content.contentEquals("[Removed]")
        }
    }
}
