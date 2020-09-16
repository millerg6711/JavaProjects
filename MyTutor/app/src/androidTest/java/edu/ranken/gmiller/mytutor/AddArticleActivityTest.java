package edu.ranken.gmiller.mytutor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AddArticleActivityTest {

    private static final int HOME_FAB = R.id.fab;
    private static final String ARTICLE_ACTIVITY_LABEL = "Add Article";
    private static final int ARTICLE_FAB = R.id.fab;
    private static final int ARTICLE_TITLE_INPUT = R.id.edit_article_title;
    private static final int ARTICLE_AUTHOR_INPUT = R.id.edit_article_author;
    private static final int ARTICLE_PUBLISHED_INPUT = R.id.edit_date_published;
    private static final int ARTICLE_TOPIC_INPUT = R.id.spinner_article_topics;
    private static final int ARTICLE_LINK_INPUT = R.id.edit_article_link;
    private static final int ERROR_INCOMPLETE = R.string.alert_dialog_error_message;
    //private static final int MESSAGE_SAVED = R.string.message_article_added;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void saveEmptyArticle() {
        // Navigate to AddArticleActivity from HomeActivity
        onView(withId(HOME_FAB)).perform(click());
        onView(withText(ARTICLE_ACTIVITY_LABEL)).check(matches(isDisplayed()));

        // Attempt to save the article
        onView(withId(ARTICLE_FAB)).perform(click());
        // Verify that error message is displayed
        onView(withText(ERROR_INCOMPLETE)).check(matches(isDisplayed()));
    }

    @Test
    public void saveValidArticle() {
        // Navigate to AddArticleActivity from HomeActivity
        onView(withId(HOME_FAB)).perform(click());
        onView(withText(ARTICLE_ACTIVITY_LABEL)).check(matches(isDisplayed()));

        // Enter article details
        String articleTitle = "My Title";
        String articleAuthor = "My Author";
        String articlePublished = "3/09/2019";
        String articleTopic = "Android";
        String articleLink = "https://google.com";
        onView(withId(ARTICLE_TITLE_INPUT)).perform(typeText(articleTitle), closeSoftKeyboard());
        onView(withId(ARTICLE_AUTHOR_INPUT)).perform(typeText(articleAuthor), closeSoftKeyboard());
        onView(withId(ARTICLE_PUBLISHED_INPUT)).perform(typeText(articlePublished), closeSoftKeyboard());
        onView(withId(ARTICLE_TOPIC_INPUT)).perform(click());
        onData(is(articleTopic)).perform(click());
        onView(withId(ARTICLE_LINK_INPUT)).perform(typeText(articleLink), closeSoftKeyboard());

        // Attempt to save the article
        onView(withId(ARTICLE_FAB)).perform(click());
        // Verify that the HomeActivity appears
        onView(withId(HOME_FAB)).check(matches(isDisplayed()));
        // Verify that success message appears
        //onView(withText(MESSAGE_SAVED)).check(matches(isDisplayed()));

        // Verify that new article is displayed
        onView(withText(articleTitle)).check(matches(isDisplayed()));
        onView(withText(articleAuthor)).check(matches(isDisplayed()));
        //onView(withText(articlePublished)).check(matches(isDisplayed()));
        //onView(withText(articleTopic)).check(matches(isDisplayed()));

        // Click on new article
        onView(withText(articleTitle)).perform(click());
    }
}
