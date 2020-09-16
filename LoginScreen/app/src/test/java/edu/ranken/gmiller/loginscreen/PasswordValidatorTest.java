package edu.ranken.gmiller.loginscreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(JUnit4.class)
public class PasswordValidatorTest {
    private PasswordValidator mValidator;

    @Before
    public void setUp() {
        mValidator = new PasswordValidator();
    }

    // Min Length = 6
    // At least one non-alphanumeric character(ie, not 0-9 or a-z)
    // Can't contain 1234
    // Cannot include whitespace characters: spaces, tabs, newlines

    @Test
    public void validPassword() {
        assertThat(mValidator.isValid("P@ssw0rd"), is(equalTo(true)));
    }

    @Test
    public void password123456() {
        assertThat(mValidator.isValid("123456"), is(equalTo(false)));
    }

    @Test
    public void emptyStr() {
        assertThat(mValidator.isValid(""), is(equalTo(false)));
    }

    @Test
    public void nullStr() {
        assertThat(mValidator.isValid(null), is(equalTo(false)));
    }

    @Test
    public void allSpaces() {
        assertThat(mValidator.isValid("          "), is(equalTo(false)));
    }

    @Test
    public void lessThanMinLength() {
        assertThat(mValidator.isValid("P@ss"), is(equalTo(false)));
    }

    @Test
    public void alphaNumeric() {
        assertThat(mValidator.isValid("abcd123456"), is(equalTo(false)));
    }

    @Test
    public void exactlyMinLength() {
        assertThat(mValidator.isValid("P@sswo"), is(equalTo(true)));
    }

    @Test
    public void containsSpacesAtEnds() {
        assertThat(mValidator.isValid("  P@ssw0rd  "), is(equalTo(false)));
    }

    @Test
    public void containsSpacesInMiddle() {
        assertThat(mValidator.isValid("P@ss     w0rd"), is(equalTo(false)));
    }

    @Test
    public void containsOtherWhitespaceCharacters() {
        assertThat(mValidator.isValid("P@ss\t\n\rword"), is(equalTo(false)));
    }

    @Test
    public void sequentialSequenceDigits() {
        assertThat(mValidator.isValid("abcd1234!"), is(equalTo(false)));
    }
}
