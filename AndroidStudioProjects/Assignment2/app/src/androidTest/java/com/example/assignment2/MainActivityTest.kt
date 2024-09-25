import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment2.MainActivity
import com.example.assignment2.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkInitialInstrumentDisplay() {
        onView(withId(R.id.nameTextView))
            .check(matches(withText("Acoustic Guitar")))

        onView(withId(R.id.priceTextView))
            .check(matches(withText("Price: 50 credits")))

        onView(withId(R.id.borrowButton))
            .check(matches(isEnabled()))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNextButtonFunctionality() {
        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.nameTextView))
            .check(matches(withText("Electric Guitar")))
        onView(withId(R.id.priceTextView))
            .check(matches(withText("Price: 70 credits")))
    }

    @Test
    fun testBorrowButtonDisablingWhenBooked() {
        onView(withId(R.id.borrowButton)).perform(click())
        onView(withId(R.id.borrowButton))
            .check(matches(not(isEnabled())))
            .check(matches(isDisplayed()))
            .check(matches(withText("Booked")))
    }
}
