package edu.ranken.gmiller.mytutor;

import android.view.inputmethod.EditorInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasImeAction;
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
public class LoginActivityTest {

    private static final String ADMIN_EMAIL = "admin@ranken.edu";
    private static final String ADMIN_PASSWORD = "P@ssw0rd";
    private static final int LOGIN_EMAIL_INPUT = R.id.edit_email;
    private static final int LOGIN_PASSWORD_INPUT = R.id.edit_password;
    private static final int LOGIN_BUTTON = R.id.button_login;
    private static final int HOME_FAB = R.id.fab;
    private static final int ERROR_INVALID_CREDENTIALS = R.string.invalid_email_or_password_error_msg;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginAsAdmin_withLoginButton() {
        String email = ADMIN_EMAIL;
        String password = ADMIN_PASSWORD;

        // Enter the username and password
        onView(withId(LOGIN_EMAIL_INPUT))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(LOGIN_PASSWORD_INPUT))
                .perform(typeText(password), closeSoftKeyboard());
        // Click the login button
        onView(withId(LOGIN_BUTTON))
                .perform(click());
        // Verify that the HomeActivity appears
        onView(allOf(withId(HOME_FAB), isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginAsAdmin_withEditorAction() throws Exception {
        String email = ADMIN_EMAIL;
        String password = ADMIN_PASSWORD;

        // Enter the username and press the NEXT button
        onView(withId(LOGIN_EMAIL_INPUT))
                .check(matches(hasImeAction(EditorInfo.IME_ACTION_NEXT)))
                .perform(typeText(email), pressImeActionButton());
        // Enter the password and press the DONE button
        onView(withId(LOGIN_PASSWORD_INPUT))
                .check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
                .perform(typeText(password), pressImeActionButton());
        // Verify that the HomeActivity appears
        onView(allOf(withId(HOME_FAB), isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginWithNoCredentials() {
        // Click the login button
        onView(withId(LOGIN_BUTTON))
                .perform(click());
        // Verify that an error message appears
        onView(withText(ERROR_INVALID_CREDENTIALS))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginWithInvalidCredentials() {
        String email = "example@ranken.edu";
        String password = "password";

        // Enter the username and password
        onView(withId(LOGIN_EMAIL_INPUT))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(LOGIN_PASSWORD_INPUT))
                .perform(typeText(password), closeSoftKeyboard());
        // Click the login button
        onView(withId(LOGIN_BUTTON))
                .perform(click());
        // Verify that an error message appears
        onView(withText(ERROR_INVALID_CREDENTIALS))
                .check(matches(isDisplayed()));
    }

}
