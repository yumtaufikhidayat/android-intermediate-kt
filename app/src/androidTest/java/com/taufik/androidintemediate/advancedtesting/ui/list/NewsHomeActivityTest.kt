package com.taufik.androidintemediate.advancedtesting.ui.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedtesting.ui.detail.NewsDetailActivity
import com.taufik.androidintemediate.advancedtesting.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewsHomeActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(NewsHomeActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadHeadlineNewsSuccess() {
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNews)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadDetailNewsSuccess() {
        Intents.init()
        onView(withId(R.id.rvNews)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(NewsDetailActivity::class.java.name))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarkSuccess() {
        onView(withId(R.id.rvNews)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
        onView(ViewMatchers.withText("BOOKMARK")).perform(click())
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNews)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
    }
}