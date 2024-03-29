package com.vullpes.newsapi.data

import androidx.paging.PagingSource
import com.google.common.truth.Truth
import com.vullpes.newsapi.data.local.ArticleDao
import com.vullpes.newsapi.data.local.ArticleDb
import com.vullpes.newsapi.data.local.NewsDatabase
import com.vullpes.newsapi.data.remote.NewsAPI
import com.vullpes.newsapi.data.remote.NewsPagingSource
import com.vullpes.newsapi.data.remote.SearchedNewsPagingSource
import com.vullpes.newsapi.data.remote.dto.ArticleDto
import com.vullpes.newsapi.data.remote.dto.NewsDto
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okio.ByteString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryImplTest{

    @Mock
    lateinit var newsAPI: NewsAPI

    @Mock
    lateinit var newsDatabase: NewsDatabase

    @Mock
    lateinit var articleDao: ArticleDao

    lateinit var newsRepositoryImpl: NewsRepositoryImpl

    val news = listOf<ArticleDto>(
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),        ArticleDto(
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )
    )

    val newsDb = listOf<ArticleDb>(
        ArticleDb(
            articleId = 1,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDb(
            articleId = 2,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        ArticleDb(
            articleId = 3,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        )

    @Before
    fun setup(){
        newsRepositoryImpl = NewsRepositoryImpl(newsAPI, newsDatabase)
    }


    @Test
    fun `testarNewsPegingSource`() = runTest {

        `when`(newsAPI.getNews(country = "br", page = 1, pageSize = 2)).thenReturn(
            Response.success(NewsDto(articles = news, status = "", totalResults = 465212))
        )
        val result = NewsPagingSource(newsAPI)

        Truth.assertThat(
            PagingSource.LoadResult.Page(
                data = news,
                prevKey = null,
                nextKey = 2
            )
        ).isEqualTo(
            result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )

    }

    @Test
    fun `testarNewsPegingSource_error`() = runTest {

        val exception = Exception("No Response", Throwable())

        `when`(newsAPI.getNews(country = "br", page = 1, pageSize = 2)).thenReturn(
            Response.error(500,ResponseBody.create(null, ByteString.EMPTY))
        )
        val result = NewsPagingSource(newsAPI)

        Truth.assertThat(
            PagingSource.LoadResult.Error<Int, ArticleDto>(exception).toString()
        ).isEqualTo(
            result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ).toString()
        )



    }


    @Test
    fun `testar search items paging source`() = runTest {

        `when`(newsAPI.getAllNewsAboutTopic(
            page = 1,
            pageSize = 2,
            q = "test"
        )).thenReturn(
            Response.success(NewsDto(articles = news, status = "", totalResults = 465212))
        )
        val result = SearchedNewsPagingSource(newsAPI, query= "test")

        Truth.assertThat(
            PagingSource.LoadResult.Page(
                data = news,
                prevKey = null,
                nextKey = 2
            )
        ).isEqualTo(
            result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )

    }


    @Test
    fun `testar search items paging source _error`() = runTest {

        val exception = Exception("No Response", Throwable())

        `when`(newsAPI.getAllNewsAboutTopic(
            page = 1,
            pageSize = 2,
            q = "test"
        )).thenReturn(
            Response.error(500,ResponseBody.create(null, ByteString.EMPTY))
        )

        val result = SearchedNewsPagingSource(newsAPI, query= "test")

        Truth.assertThat(
            PagingSource.LoadResult.Error<Int, ArticleDto>(exception).toString()
        ).isEqualTo(
            result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ).toString()
        )

    }



    @Test
    fun `testar getSavedNews()`() = runTest {
        `when`(newsDatabase.articleDao()).thenReturn(articleDao)
        `when`(newsDatabase.articleDao().getSavedNews()).thenReturn(
            flow {
                emit(newsDb)
            }
        )

        val result = newsRepositoryImpl.getSavedNews().first()

        Truth.assertThat(result[0]).isInstanceOf(Article::class.java)
    }




}