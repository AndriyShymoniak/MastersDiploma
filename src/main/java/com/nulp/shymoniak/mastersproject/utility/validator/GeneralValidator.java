package com.nulp.shymoniak.mastersproject.utility.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.URL_REGEX_PATTERN;

@Component
public class GeneralValidator {
    public boolean isValidURL(String url) {
        return isNotNullAndNotBlank(url) && Pattern.matches(URL_REGEX_PATTERN, url);
    }

    public boolean isNotNullAndNotBlank(String str) {
        return str != null && !str.isBlank();
    }
}
