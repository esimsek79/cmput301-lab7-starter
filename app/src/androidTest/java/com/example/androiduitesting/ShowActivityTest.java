package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    // helper: adds a single city through the ui and returns to the list
    private void addCity(String cityName) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(cityName));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    // verify that tapping a list item launches ShowActivity by checking
    // that a view unique to ShowActivity (the back button) is now visible
    @Test
    public void testActivitySwitch() {
        addCity("Edmonton");

        // tap the first item in the list to trigger the activity transition
        onData(anything()).inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // the back button only exists in ShowActivity, its presence confirms the switch
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    // verify that the city name shown in ShowActivity matches what was tapped in the list
    @Test
    public void testCityNameConsistency() {
        addCity("Vancouver");

        // tap the city to open ShowActivity
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // the text view in ShowActivity should display the exact same name
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    // verify that pressing the back button returns the user to MainActivity
    // by confirming a view that only exists in MainActivity is displayed again
    @Test
    public void testBackButton() {
        addCity("Calgary");

        // navigate to ShowActivity
        onData(anything()).inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // press the in-app back button inside ShowActivity
        onView(withId(R.id.button_back)).perform(click());

        // the add button belongs to MainActivity. presence confirms we returned
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}