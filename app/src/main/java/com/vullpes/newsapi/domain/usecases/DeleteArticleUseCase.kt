package com.vullpes.newsapi.domain.usecases

import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.model.Article
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(article: Article){
        repository.deleteArticle(article)
    }
}