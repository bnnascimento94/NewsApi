package com.vullpes.newsapi.domain.usecases

import android.util.Log
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.domain.NewsRepository
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
class GetLatestNewsUsecaseTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val testDispatcher = StandardTestDispatcher()


    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Inject
    lateinit var  newsRepository: NewsRepository

    lateinit var  getLatestNewsUsecase: GetLatestNewsUsecase

    @Before
    fun setup() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        getLatestNewsUsecase = GetLatestNewsUsecase(newsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testarFiltoParaQueNaoTenhaNenhumRemovedTitleOuRemovedContent() = runTest{
        val differ = AsyncPagingDataDiffer(
            diffCallback = UltimasNoticiasAdapter.REPO_COMPARATOR,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher,
        )

        val job = launch {
            getLatestNewsUsecase.execute().collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        // Wait for initial load to finish.
        advanceUntilIdle()

        val teste = differ.snapshot().items.filter { article -> article.title != "[Removed]"
                &&
                article.content != "[Removed]"
        }
        Log.e("items", teste.toString())
        Truth.assertThat(differ.snapshot().size).isEqualTo(1)

        job.cancel()
    }



}