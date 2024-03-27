package com.vullpes.newsapi.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.data.local.ArticleDao
import com.vullpes.newsapi.data.local.ArticleDb
import com.vullpes.newsapi.data.local.NewsDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NewsDatabaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: NewsDatabase
    private lateinit var dao: ArticleDao


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.articleDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArticleInDatabase() = runTest {
        val articleDb = ArticleDb(
            articleId = 0,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )
        dao.saveArticleDb(articleDb)

        val result = dao.getSavedNews().first()

        Truth.assertThat(result[0].author).isEqualTo("Bruno Nunes")
    }

    @Test
    fun deleteArticleDatabase() = runTest {
        val articleDb = ArticleDb(
            articleId = 0,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620253",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )
        dao.saveArticleDb(articleDb)
        val articleDbSaved = dao.getSavedNews().first()
        dao.deleteArticleDb(articleDbSaved[0])
        val remainedAreticles = dao.getSavedNews().first()

        Truth.assertThat(remainedAreticles).isEmpty()
    }


}