package com.example.assignment2

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testBorrowButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        // Check if the "Borrow" button is displayed
        onView(withId(R.id.borrowButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testClickBorrowButtonOpensDetailActivity() {
        ActivityScenario.launch(MainActivity::class.java)

        // Click the "Borrow" button
        onView(withId(R.id.borrowButton))
            .perform(click())

        //Check if DetailActivity is opened by checking a View in DetailActivity
        onView(withId(R.id.nameTextView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNextButtonChangesInstrument() {
        ActivityScenario.launch(MainActivity::class.java)

        //Get the current instrument name
        var currentName = ""
        onView(withId(R.id.nameTextView))
            .check { view, _ ->
                val textView = view as TextView
                currentName = textView.text.toString()
            }

        // Click the "Next" button
        onView(withId(R.id.nextButton))
            .perform(click())

        //Check if the instrument name has changed
        onView(withId(R.id.nameTextView))
            .check(matches(not(withText(currentName))))
    }
}
