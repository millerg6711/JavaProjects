package edu.ranken.gmiller.scorekeeper;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DayModeAndNightModeTextAppearsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void dayModeTextAppearsTest() {
        clickMoreOptions();

        ViewInteraction nightModeOption1 = onView(
            allOf(withId(R.id.title), withText("Night Mode"),
                isDisplayed()));
        nightModeOption1.perform(click());

        clickMoreOptions();

        ViewInteraction dayModeOption1 = onView(
            allOf(withId(R.id.title), withText("Day Mode"),
                isDisplayed()));
        dayModeOption1.check(matches(withText("Day Mode")));
        dayModeOption1.perform(click());

        clickMoreOptions();

        ViewInteraction nightModeOption2 = onView(
            allOf(withId(R.id.title), withText("Night Mode"),
                isDisplayed()));
        nightModeOption2.check(matches(withText("Night Mode")));
    }

    private void clickMoreOptions() {
        ViewInteraction moreOptions = onView(
            allOf(withContentDescription("More options"),
                isDisplayed()));
        moreOptions.perform(click());
    }

}
