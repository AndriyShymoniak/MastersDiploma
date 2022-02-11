package com.nulp.shymoniak.mastersproject.utility.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.*;

@Component
public class ValidationUtilityImpl implements ValidationUtility {
    @Override
    public boolean isNotNullAndNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    @Override
    public boolean isValidURL(String url) {
        return isNotNullAndNotBlank(url) && Pattern.matches(URL_REGEX_PATTERN, url);
    }

    @Override
    public boolean isValidEmail(String email) {
        return isNotNullAndNotBlank(email) && Pattern.matches(EMAIL_REGEX_PATTERN, email);
    }

    @Override
    public boolean isValidStringWithCapitalLettersAndUnderscores(String input) {
        return isNotNullAndNotBlank(input) && Pattern.matches(CAPITAL_LETTERS_WITH_UNDERSCORE_REGEX_PATTERN, input);
    }

    // TODO: 2/11/22 create implementation and test
    @Override
    public boolean isValidBooleanRepresentedWithInteger(Integer input) {
        return true;
    }
}
