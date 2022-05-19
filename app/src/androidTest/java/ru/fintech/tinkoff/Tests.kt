package ru.fintech.tinkoff

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.runner.RunWith
import org.junit.*
import org.wikipedia.main.MainActivity
import org.wikipedia.R

@RunWith(AndroidJUnit4::class)
class Tests {
    @get: Rule
    var activityActivityTestRule = ActivityTestRule(
        MainActivity::class.java
    )
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun skipTutorial(){
        onView(withId(R.id.nav_more_container))
            .perform(click())
    }

}