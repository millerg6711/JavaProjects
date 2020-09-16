package edu.ranken.gmiller.loginscreen;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class PasswordValidator {
    // Min Length = 6
    // At least one non-alphanumeric character(ie, not 0-9 or a-z)
    // Can't contain 1234
    // Cannot include whitespace characters: spaces, tabs, newlines
    public boolean isValid(String password) {

        if (password == null ||
            password.length() < 6 ||
            password.contains("1234")) {
            return false;
        }

        boolean containsNonAlphaNumChar = false;
        boolean containsWhiteSpace = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isWhitespace(password.charAt(i))) {
                containsWhiteSpace = true;
            }
            else if (!Character.isLetterOrDigit(password.charAt(i))) {
                containsNonAlphaNumChar = true;
            }
        }

        return containsNonAlphaNumChar && !containsWhiteSpace;
    }


}
