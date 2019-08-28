package com.google.samples.apps.sunflower

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.google.samples.apps.sunflower.mvvm.garden.GardenActivity
import org.junit.Rule
import org.junit.Test

class GardenActivityTest {

    @Rule @JvmField
    var activityTestRule = ActivityTestRule(GardenActivity::class.java)

    @Test
    fun clickAddPlant_OpensPlantList() {
        // Given that no Plants are added to the user's garden

        // When the "Add Plant" button is clicked
        onView(withId(R.id.add_plant)).perform(click())

        // Then the ViewPager should change to the Plant List page
        onView(withId(R.id.plant_list)).check(matches(isDisplayed()))
    }
}
