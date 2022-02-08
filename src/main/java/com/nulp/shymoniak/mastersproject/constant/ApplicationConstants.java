package com.nulp.shymoniak.mastersproject.constant;

public class ApplicationConstants {
    // Exception messages
    public static final String ERROR_MESSAGE_RECORD_NOT_FOUND = "There is no such record";
    public static final String ERROR_INVALID_ENTITY = "Entity is not valid";

    // Is used in DistanceCalculator
    public static final Integer EARTH_RADIUS = 6371; //Kilometers

    // URL regex pattern
    public static final String  URL_REGEX_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
}