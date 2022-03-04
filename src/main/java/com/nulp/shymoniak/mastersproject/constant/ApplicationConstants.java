package com.nulp.shymoniak.mastersproject.constant;

public class ApplicationConstants {
    // Exception messages
    public static final String ERROR_MESSAGE_RECORD_NOT_FOUND = "There is no such record";
    public static final String ERROR_INVALID_ENTITY = "Entity is not valid";

    // Used in DistanceCalculator
    public static final Integer EARTH_RADIUS = 6371; //Kilometers

    // Regex patterns
    public static final String URL_REGEX_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String CAPITAL_LETTERS_WITH_UNDERSCORE_REGEX_PATTERN = "^([A-Z]+_)*[A-Z]+$";
    public static final String USERNAME_AND_PASSWORD_REGEX_PATTERN = "^[a-zA-Z]+[a-zA-Z0-9._-]{4,}$";

    // Default flag values
    public static final Integer DEFAULT_TRUE_FLAG = 1;
    public static final Integer DEFAULT_FALSE_FLAG = 0;

    // JWT
    public static final String JWT_AUTHORITIES = "authorities";
    public static final String JWT_AUTHORITY = "authority";
}