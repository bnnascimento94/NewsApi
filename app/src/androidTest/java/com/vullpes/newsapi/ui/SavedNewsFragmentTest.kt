package com.vullpes.newsapi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.R
import com.vullpes.newsapi.domain.model.Article
import com.vullpes.newsapi.getOrAwaitValue
import com.vullpes.newsapi.launchFragmentInHiltContainer
import com.vullpes.newsapi.ui.adapters.NoticiasSalvasAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SavedNewsFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun deletarVisitaOnSwipeLeft(){
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
        var testViewModel: NewsViewModel? = null
        launchFragmentInHiltContainer<SavedNewsFragment> {
            testViewModel = viewModel
            viewModel.salvarNoticias(article)
        }

        Espresso.onView(withId(R.id.rvSaved)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NoticiasSalvasAdapter.ViewHolder>(
                0, ViewActions.swipeLeft()
            )
        )

        val artigosSalvosResult = testViewModel?.buscarNoticiasSalvas()?.getOrAwaitValue()
        Truth.assertThat(artigosSalvosResult?.size).isEqualTo(0)

    }

}