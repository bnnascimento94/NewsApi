package com.vullpes.newsapi.domain.usecases

import com.google.common.truth.Truth
import com.vullpes.newsapi.FakeNewsApiRepository
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class SaveNewsUserCaseTest {

    lateinit var fakeNewsApiRepository: FakeNewsApiRepository

    lateinit var insertArticleUseCase: SaveNewsUserCase
    @Before
    fun setup(){
        fakeNewsApiRepository = FakeNewsApiRepository()
        insertArticleUseCase = SaveNewsUserCase(fakeNewsApiRepository)
    }


    @Test
    fun `testar inserir article usecase`() = runTest {
        val article = Article(
            articleId = 1,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620CVX25311",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )

        insertArticleUseCase.invoke(article)

        Truth.assertThat(fakeNewsApiRepository.savedArticles[0]).isEqualTo(article)
    }
}