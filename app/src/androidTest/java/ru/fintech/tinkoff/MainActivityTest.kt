package ru.fintech.tinkoff

import android.Manifest
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
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
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.runner.RunWith
import org.wikipedia.R
import org.wikipedia.main.MainActivity
import java.util.concurrent.TimeUnit
import org.junit.runners.Parameterized


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

    fun clickMore() {
        onView(withId(R.id.menu_icon)).perform(click())
    }

    fun clickDonate() {
        onView(withId(R.id.main_drawer_donate_container)).perform(click())
    }

    fun clickSettings() {
        onView(withId(R.id.main_drawer_settings_container)).perform(click())
    }

    fun clickToString() {
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
    fun testCaseOne() {
        step("Проверяем отображение главной страницы") {
            val newMainActivityActions = MainActivityTest()
            newMainActivityActions.clickMore()
            Intents.init()
            newMainActivityActions.clickDonate()
            val expectedIntent = allOf(
                hasAction(Intent.ACTION_CHOOSER),
                hasExtraWithKey(Intent.EXTRA_EXCLUDE_COMPONENTS)
            )
            intended(expectedIntent)
            Intents.release()
        }
        allureScreenshot("page_display1")
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
                    .check(matches(isDisplayed()))
            }
        }
        allureScreenshot("page_display3")
    }

//    @Test //TODO "Доделать четвертый кейс"
//    @AllureId("4")
//    @Tag("no")
//    @DisplayName("Четвертый кейс")
//    fun testCaseFour() {
//        step("Проверка, что пароль скрывается, если нажать на глазик два раза") {
//            val newMainActivityActions = MainActivityTest()
//            newMainActivityActions.clickMore()
//            newMainActivityActions.clickSettings()
//            Awaitility.await().atMost(2, TimeUnit.SECONDS).untilAsserted {
//
//                onView(withId(R.id.main_drawer_login_button))
//                    .perform(click())
//                onView(withId(R.id.create_account_password_input))
//                    .perform(click())
////                onView(withText(R.string.text_input_end_icon))
////                    .check(matches(isDisplayed()))
//            }
//        }
//        allureScreenshot("page_display3")
//    }
}


@RunWith(Parameterized::class)
class ParametrizedTests(private val pathToButtonCheckBoxes: Int) {
    @get: Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() : List<Int> {
            return listOf(
                R.string.view_featured_article_card_title,
                R.string.view_featured_article_card_title,
                R.string.view_featured_image_card_title,
                R.string.view_because_you_read_card_title,
                R.string.view_card_news_title,
                R.string.on_this_day_card_title,
                R.string.view_random_card_title,
                R.string.view_main_page_card_title,
            )
        }
    }

    @Test
    fun testCaseSecond() {
        onView(withId(R.id.fragment_onboarding_skip_button))
            .perform(click())
        onView(withId(R.id.menu_icon)).perform(click())
        onView(withId(R.id.main_drawer_settings_container)).perform(click())
        onView(withText(R.string.preference_title_customize_explore_feed))
            .perform(click())
        if  (pathToButtonCheckBoxes == R.string.view_main_page_card_title ||
        pathToButtonCheckBoxes == R.string.view_random_card_title) {
            onView(withId(R.id.content_types_recycler)).perform(swipeUp())}

        onView(
            allOf(
                withId(R.id.feed_content_type_checkbox),
                withParent(withChild(withChild(withText(pathToButtonCheckBoxes))))
            )
        ).check(matches(isChecked()))
    }
}