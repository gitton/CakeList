package com.waracle.cakelist

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CakeListFeature {

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun displayCakeListOnLoad() {
        // Context of the app under test.
        assertDisplayed(R.id.rv_cake_list)
    }

}