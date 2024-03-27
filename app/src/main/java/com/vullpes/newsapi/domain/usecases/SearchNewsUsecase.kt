package com.vullpes.newsapi.domain.usecases

import androidx.paging.filter
import com.vullpes.newsapi.domain.NewsRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchNewsUsecase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(search: String) = newsRepository.searchNews(search).map {
        it.filter { article -> article.title != "[Removed]" || article.content != "[Removed]"}
    }
}