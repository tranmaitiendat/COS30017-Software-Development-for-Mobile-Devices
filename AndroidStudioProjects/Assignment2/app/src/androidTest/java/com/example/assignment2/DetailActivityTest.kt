package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.RatingBar
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.chip.Chip
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    private val instrument = Instrument(
        name = "Test Instrument",
        rating = 3.0f,
        attributes = listOf("Test Attribute"),
        rentalPrice = 100,
        imageResourceId = R.drawable.acoustic_guitar
    )

    @Test
    fun testDisplayInstrumentDetails() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            DetailActivity::class.java
        ).apply {
            putExtra("instrument", instrument)
        }

        ActivityScenario.launch<DetailActivity>(intent).use {
            // Check instrument name
            onView(withId(R.id.nameTextView))
                .check(matches(withText("Test Instrument")))

            // Check price
            onView(withId(R.id.priceTextView))
                .check(matches(withText("Price: 100 credits")))


        }
    }

    @Test
    fun testSaveButtonWithoutAgree() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            DetailActivity::class.java
        ).apply {
            putExtra("instrument", instrument)
        }

        ActivityScenario.launch<DetailActivity>(intent).use {
            // Press the "Save" button without turning on the OK Switch
            onView(withId(R.id.saveButton))
                .perform(click())

            // Check if error message is displayed
            onView(withText("You must agree to the terms."))
                .inRoot(withDecorView(not(CoreMatchers.`is`(getActivity(it)?.window?.decorView))))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun testSaveButtonWithAgree() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            DetailActivity::class.java
        ).apply {
            putExtra("instrument", instrument)
        }

        ActivityScenario.launch<DetailActivity>(intent).use { scenario ->
            // Turn on the Agree Switch
            onView(withId(R.id.agreeSwitch))
                .perform(click())

            // Select some properties
            onView(allOf(withText("Acoustic"), isAssignableFrom(Chip::class.java)))
                .perform(click())



            // Click the "Save" button
            onView(withId(R.id.saveButton))
                .perform(click())

            //Check if Activity has finished
            assert(scenario.result.resultCode == Activity.RESULT_OK)
        }
    }

    // Helper function to get Activity from ActivityScenario
    private fun getActivity(scenario: ActivityScenario<DetailActivity>): DetailActivity? {
        var activity: DetailActivity? = null
        scenario.onActivity {
            activity = it
        }
        return activity
    }


    }

