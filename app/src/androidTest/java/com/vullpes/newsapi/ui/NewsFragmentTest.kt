package com.vullpes.newsapi.ui

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vullpes.newsapi.HiltTestActivity
import com.vullpes.newsapi.R
import com.vullpes.newsapi.domain.model.Article
import com.vullpes.newsapi.getOrAwaitValue
import com.vullpes.newsapi.launchFragmentInHiltContainer
import com.vullpes.newsapi.ui.adapters.NoticiasSalvasAdapter
import com.vullpes.newsapi.ui.adapters.UltimasNoticiasAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NewsFragmentTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val intentsTestRule = IntentsTestRule(HiltTestActivity::class.java)

    @Before
    fun setup(){
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                null
            )
        )
    }


    @Test
    fun testarAbrirArticleNavegador(){

        launchFragmentInHiltContainer<NewsFragment> {}

        onView(withId(R.id.newsApi))
            .perform(RecyclerViewActions.actionOnItemAtPosition<NoticiasSalvasAdapter.ViewHolder>(0, click()));

        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW)
            )
        )


    }




}