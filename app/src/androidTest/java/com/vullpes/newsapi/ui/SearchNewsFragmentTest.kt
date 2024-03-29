package com.vullpes.newsapi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.R
import com.vullpes.newsapi.getOrAwaitValue
import com.vullpes.newsapi.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchNewsFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun btnBuscarAcaoPesquisarNoticias(){

        var testViewModel: NewsViewModel? = null
        launchFragmentInHiltContainer<SearchNewsFragment> {
            testViewModel = viewModel
        }
        Espresso.onView(withId(R.id.txtPesquisar)).perform(typeText("password"))
        Espresso.onView(withId(R.id.btnPesquisar)).perform(click())
        Espresso.onView(withId(R.id.rvSearchedNews)).check(matches(hasChildCount(1)))

    }

    @Test
    fun testarClickSalvarArtigo(){

        var testViewModel: NewsViewModel? = null
        launchFragmentInHiltContainer<SearchNewsFragment> {
            testViewModel = viewModel
        }
        Espresso.onView(withId(R.id.txtPesquisar)).perform(typeText("trump"))
        Espresso.onView(withId(R.id.btnPesquisar)).perform(click())
        Espresso.onView(withId(R.id.btnSaveButton)).perform(click())

        val artigosSalvosResult = testViewModel?.buscarNoticiasSalvas()?.getOrAwaitValue()
        Truth.assertThat(artigosSalvosResult?.size).isEqualTo(1)

    }


}