package com.vullpes.newsapi.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
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



    @Test //fails
    fun navigationMenu_contractClicked_openContractFragment() {
        onView(withId(R.id.searchNewsFragment)).perform(click())
        //onView(withText("Hello Steve!")).check(matches(isDisplayed()))



    }



}