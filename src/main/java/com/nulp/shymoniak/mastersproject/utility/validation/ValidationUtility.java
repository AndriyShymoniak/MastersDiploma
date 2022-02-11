package com.nulp.shymoniak.mastersproject.utility.validation;

public interface ValidationUtility {
    boolean isNotNullAndNotBlank(String str);

    boolean isValidURL(String url);

    boolean isValidEmail(String email);

    boolean isValidStringWithCapitalLettersAndUnderscores(String input);

    boolean isValidBooleanRepresentedWithInteger(Integer input);
}