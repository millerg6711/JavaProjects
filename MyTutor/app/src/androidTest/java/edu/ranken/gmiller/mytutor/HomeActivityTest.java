package edu.ranken.gmiller.mytutor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class HomeActivityTest {

    private static final int HOME_FAB = R.id.fab;
    private static final int HOME_RECYCLERVIEW = R.id.recycler_view;
    private static final String[] TOPICS = {
        "Java", "Android", "WebDev"
    };

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void fabVisible() {
        // Check if the FAB is visible
        onView(withId(HOME_FAB)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewVisible() {
        // Check if the RecyclerView is visible
        onView(allOf(withId(HOME_RECYCLERVIEW), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void topicsVisible() {
        // Load the topics
        //String[] topics = mActivityRule.getActivity().getResources().getStringArray(R.array.array_topics);
        String[] topics = TOPICS;

        // Try each topic in turn
        for (String topic : topics) {
            onView(withText(topic))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }
    }
}
