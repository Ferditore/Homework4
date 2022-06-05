package ru.fintech.tinkoff

import android.Manifest
import android.content.Context.*
import android.content.Intent
import android.graphics.Rect
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import io.qameta.allure.android.allureScreenshot
import io.qameta.allure.android.rules.ScreenshotRule
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.*
import io.qameta.allure.kotlin.Allure.step
import io.qameta.allure.kotlin.junit4.DisplayName
import io.qameta.allure.kotlin.junit4.Tag
import org.awaitility.Awaitility
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.wikipedia.R
import org.wikipedia.main.MainActivity
import java.util.concurrent.TimeUnit


@get:Rule
val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)


@RunWith(AllureAndroidJUnit4::class)
@Epic("Tinkoff Fintech")
@Feature("Cases")
@DisplayName("UI Tests")
internal class MainActivityTest {

    @get: Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)
    val logcatRule = ScreenshotRule(mode = ScreenshotRule.Mode.END, screenshotName = "ss_end")

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

    private fun clickSettings(){
        onView(withId(R.id.main_drawer_settings_container)).perform(click())
    }

    private  fun clickToString(){
        onView(withText(R.string.preference_title_customize_explore_feed))
        .perform(click())
    }

    private fun findAboutApp() {

        for (i in 0..4) {
            onView(
                allOf(
                    withId(R.id.recycler_view),
                    isDisplayed()
                )
            ).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(15, swipeUp()))
        }

        onView(withText(R.string.about_description))
            .perform(swipeUp()).check(matches(isDisplayed()))
        onView(withText(R.string.about_description))
            .perform(click())
    }


    @Test
    @AllureId("1")
    @Tag("no")
    @DisplayName("Первый кейс")
    fun testCaseOne(){
        step("Проверяем отображение главной страницы") {
            val newMainActivityActions = MainActivityTest()
            newMainActivityActions.clickMore()
            Intents.init()
            newMainActivityActions.clickDonate()
            val expectedIntent = hasExtraWithKey(Intent.EXTRA_INTENT)
            intended(expectedIntent)
            Intents.release()
        }
        allureScreenshot("page_display1")
    }

    @Test
    @AllureId("2")
    @Tag("no")
    @DisplayName("Второй кейс")
    fun testCaseTwo(){
        step("Проверка настройки ленты по умолчанию") {
            val newMainActivityActions = MainActivityTest()
            newMainActivityActions.clickMore()
            newMainActivityActions.clickSettings()
            newMainActivityActions.clickToString()
//        onView(withId(R.id.feed_content_type_checkbox))
            allureScreenshot("step_page_display2")
        }
        allureScreenshot("page_display2")
    }

    @Test
    @AllureId("3")
    @Tag("no")
    @DisplayName("Третий кейс")
    fun testCaseThree() {
        step("Проверка блоков на экране \"О приложении\"") {
            val newMainActivityActions = MainActivityTest()
            newMainActivityActions.clickMore()
            newMainActivityActions.clickSettings()
            newMainActivityActions.findAboutApp()
            Awaitility.await().atMost(2, TimeUnit.SECONDS).untilAsserted {

            onView(withText(R.string.about_contributors_heading))
                .check(matches(isDisplayed()))
            onView(withText(R.string.about_translators_heading))
                .check(matches(isDisplayed()))
            onView(withText(R.string.about_app_license_heading))
                .check(matches(isDisplayed()))   }
        }
        allureScreenshot("page_display3")
    }
}