package com.waracle.cakelist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.waracle.cakelist.di.EspressoIdllingResource
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CakeListFeature {

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setup(){
        IdlingRegistry.getInstance().register(EspressoIdllingResource.countingIdlingResource)
    }

    @Test
    fun displayCakeListOnLoad() {
        // Context of the app under test.
        assertListNotEmpty(R.id.rv_cake_list)
    }


    @Test
    fun displayCakeDescriptionOnClick() {
        // Context of the app under test.
        clickListItem(R.id.rv_cake_list,0)
        onView(withText(R.string.cake_details))
            .inRoot(isDialog())
            .check(matches(isDisplayed()));

    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdllingResource.countingIdlingResource)
    }

}