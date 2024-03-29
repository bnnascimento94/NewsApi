package com.vullpes.newsapi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.domain.model.Article
import com.vullpes.newsapi.domain.usecases.DeleteArticleUseCase
import com.vullpes.newsapi.domain.usecases.GetLatestNewsUsecase
import com.vullpes.newsapi.domain.usecases.GetSavedNewsUsecase
import com.vullpes.newsapi.domain.usecases.SaveNewsUserCase
import com.vullpes.newsapi.domain.usecases.SearchNewsUsecase
import com.vullpes.newsapi.getOrAwaitValue
import com.vullpes.newsapi.ui.adapters.UltimasNoticiasAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NewsViewModelTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Inject
    lateinit var  getLatestNewsUsecase: GetLatestNewsUsecase
    @Inject
    lateinit var getSavedNewsUsecase: GetSavedNewsUsecase
    @Inject
    lateinit var saveNewsUserCase: SaveNewsUserCase
    @Inject
    lateinit var searchNewsUsecase: SearchNewsUsecase
    @Inject
    lateinit var deleteArticleUseCase: DeleteArticleUseCase

    lateinit var newsViewModel: NewsViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        newsViewModel = NewsViewModel(getLatestNewsUsecase, getSavedNewsUsecase, saveNewsUserCase, searchNewsUsecase, deleteArticleUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun `testar`() = runTest{

        val differ = AsyncPagingDataDiffer(
            diffCallback = UltimasNoticiasAdapter.REPO_COMPARATOR,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher,
        )

        val job = launch {
            newsViewModel.buscarUltimasNoticias().collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        // Wait for initial load to finish.
        advanceUntilIdle()


        Truth.assertThat(differ.snapshot()[0]).isEqualTo(
            Article(
                articleId = 1,
                author = "Bruno Nunes",
                content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
                description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
                publishedAt = "2024-03-20T17:50:49Z",
                title = "Woman found with £2bn in Bitcoin convicted",
                url =  "https://www.bbc.co.uk/news/uk-england-london-68620CVX25311",
                urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
            )
        )

        job.cancel()
    }


    @Test
    fun verificar_articles_saved() = runTest {
        val articleInserted =  Article(
            articleId = 1,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620CVX25311",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )

        newsViewModel.salvarNoticias(article = articleInserted)

        val noticiasSaved = newsViewModel.buscarNoticiasSalvas().getOrAwaitValue()

        Truth.assertThat(noticiasSaved.size).isEqualTo(1)
    }


    @Test
    fun verificar_articles_removed() = runTest {
        val articleInserted =  Article(
            articleId = 1,
            author = "Bruno Nunes",
            content = "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]",
            description = "Jian Wen, 42, from north London, was involved in converting Bitcoin into assets like luxury houses.",
            publishedAt = "2024-03-20T17:50:49Z",
            title = "Woman found with £2bn in Bitcoin convicted",
            url =  "https://www.bbc.co.uk/news/uk-england-london-68620CVX25311",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/17FE5/production/_132977289_wen2.jpg\""
        )

        newsViewModel.salvarNoticias(article = articleInserted)

        newsViewModel.deletarNoticia(article = articleInserted)

        val noticiasSaved = newsViewModel.buscarNoticiasSalvas().getOrAwaitValue()

        Truth.assertThat(noticiasSaved.size).isEqualTo(0)
    }








}