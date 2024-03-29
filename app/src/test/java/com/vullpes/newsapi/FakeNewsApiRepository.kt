package com.vullpes.newsapi

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vullpes.newsapi.domain.NewsRepository
import com.vullpes.newsapi.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNewsApiRepository : NewsRepository {



    val newsRepository = listOf<Article>(
        Article(
            articleId = 1,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620CVX25311",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        Article(
            articleId = 2,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "[Removed]",
            url =  "https://www.bbc.co.uk/news/uk-england-london-686AFGFJHJH202533132",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        ),
        Article(
            articleId = 3,
            author = "Bruno Nunes",
            content = "[Removed]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620asd25321321",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )
    )

    val savedArticles = mutableListOf<Article>()


    val pagingSource = object : PagingSource<Int, Article>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
            return LoadResult.Page(
                data = newsRepository,
                prevKey = null,
                nextKey = null,
            )
        }

        // Ignored in test.
        override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
    }

    val allNews: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 2,
            enablePlaceholders = true
        )
    ) {
        pagingSource
    }.flow

    override fun getLatestNews(): Flow<PagingData<Article>> = allNews


    override fun getSavedNews(): Flow<List<Article>> = flowOf(newsRepository)

    override fun searchNews(search: String): Flow<PagingData<Article>> = allNews

    override suspend fun insertArticle(article: Article) {
        savedArticles.add(article)
    }

    override suspend fun deleteArticle(article: Article) {
        savedArticles.remove(article)
    }
}