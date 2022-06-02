package ru.fintech.tinkoff

import android.content.Intent
import android.widget.FrameLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.runner.RunWith
import org.junit.*
import org.junit.Assert.*
import org.wikipedia.main.MainActivity
import org.wikipedia.R
import kotlin.math.exp

@RunWith(AndroidJUnit4::class)
internal class MainActivityTest {
    @get: Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun skipTutorial() {
        onView(withId(R.id.fragment_onboarding_skip_button))
            .perform(click())
    }


    private fun clickMore(){
        onView(withId(R.id.menu_icon)).perform(click())
    }

    private fun clickDonate(){
        onView(withId(R.id.main_drawer_donate_container)).perform(click())
    }

    @Test
    fun testCaseOne(){
        val newMainActivityActions = MainActivityTest()
        newMainActivityActions.clickMore()
        Intents.init()
        newMainActivityActions.clickDonate()
        val expectedIntent = hasExtraWithKey(Intent.EXTRA_INTENT)
        intended(expectedIntent)
        Intents.release()
    }
}