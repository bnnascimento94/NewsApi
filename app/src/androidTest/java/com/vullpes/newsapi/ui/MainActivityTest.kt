package com.vullpes.newsapi.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vullpes.newsapi.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1) // 2
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun navigationMenu_click_search_option_openSearchNewsFragment() {
        onView(withId(R.id.searchNewsFragment)).perform(click())
        onView(withId(R.id.rvSearchedNews)).check(matches(isDisplayed()))

    }

    @Test
    fun navigationMenu_click_saved_option_openSavedNewsFragment() {
        onView(withId(R.id.savedNewsFragment)).perform(click())
        onView(withId(R.id.rvSaved)).check(matches(isDisplayed()))
    }


    @Test
    fun navigationMenu_click_latestNews_option_openNewsFragment() {
        onView(withId(R.id.newsFragment)).perform(click())
        onView(withId(R.id.newsApi)).check(matches(isDisplayed()))
    }



}