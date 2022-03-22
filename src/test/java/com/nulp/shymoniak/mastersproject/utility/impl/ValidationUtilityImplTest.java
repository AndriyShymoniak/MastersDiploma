package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.utility.impl.ValidationUtilityImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationUtilityImplTest {
    private final ValidationUtilityImpl validationUtility;

    @Autowired
    public ValidationUtilityImplTest(ValidationUtilityImpl validationUtility) {
        this.validationUtility = validationUtility;
    }

    @Test
    void isNotNullAndNotBlank_shouldReturnFalse_whenStringIsBlank() {
        // Given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        // Then
        assertFalse(validationUtility.isNotNullAndNotBlank(nullString));
        assertFalse(validationUtility.isNotNullAndNotBlank(emptyString));
        assertFalse(validationUtility.isNotNullAndNotBlank(stringWithSpaces));
    }

    @Test
    void isNotNullAndNotBlank_shouldReturnTrue_whenStringIsNotBlank() {
        // Given
        String validString1 = "s";
        String validString2 = "A random string";
        // Then
        assertTrue(validationUtility.isNotNullAndNotBlank(validString1));
        assertTrue(validationUtility.isNotNullAndNotBlank(validString2));
    }

    @Test
    void isValidURL_shouldReturnFalse_whenBlankRandomOrDoesNotMeetStandards() {
        // Given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String randomString = "A random string";
        String invalidURL1 = "https://";
        String invalidURL2 = "github.com/";
        // Then
        assertFalse(validationUtility.isValidURL(nullString));
        assertFalse(validationUtility.isValidURL(emptyString));
        assertFalse(validationUtility.isValidURL(stringWithSpaces));
        assertFalse(validationUtility.isValidURL(randomString));
        assertFalse(validationUtility.isValidURL(invalidURL1));
        assertFalse(validationUtility.isValidURL(invalidURL2));
    }

    @Test
    void isValidURL_shouldReturnTrue_whenMeetsStandards() {
        // Given
        String validUrl1 = "https://github.com/";
        String validUrl2 = "https://shorturl.at/elnI5";
        String validUrl3 = "https://helpx.adobe.com/photoshop/using/convert-color-image-black-white.html";
        // Then
        assertTrue(validationUtility.isValidURL(validUrl1));
        assertTrue(validationUtility.isValidURL(validUrl2));
        assertTrue(validationUtility.isValidURL(validUrl3));
    }

    @Test
    void isValidEmail_shouldReturnFalse_whenBlankRandomOrDoesNotMeetStandards() {
        // Given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String randomString = "A random string";
        String invalidEmail1 = "qwerty@";
        String invalidEmail2 = "@mail";
        String invalidEmail3 = "Abc.example.com";
        String invalidEmail4 = "A@b@c@example.com";
        String invalidEmail5 = "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com";
        String invalidEmail6 = "not\\allowed@example.com";
        String invalidEmail7 = "i_like_underscore@but_its_not_allowed_in_this_part.example.com";
        // Then
        assertFalse(validationUtility.isValidEmail(nullString));
        assertFalse(validationUtility.isValidEmail(emptyString));
        assertFalse(validationUtility.isValidEmail(stringWithSpaces));
        assertFalse(validationUtility.isValidEmail(randomString));
        assertFalse(validationUtility.isValidEmail(invalidEmail1));
        assertFalse(validationUtility.isValidEmail(invalidEmail2));
        assertFalse(validationUtility.isValidEmail(invalidEmail3));
        assertFalse(validationUtility.isValidEmail(invalidEmail4));
        assertFalse(validationUtility.isValidEmail(invalidEmail5));
        assertFalse(validationUtility.isValidEmail(invalidEmail6));
        assertFalse(validationUtility.isValidEmail(invalidEmail7));
    }

    @Test
    void isValidEmail_shouldReturnTrue_whenMeetsStandards() {
        // Given
        String validEmail1 = "qwerty@mail.com";
        String validEmail2 = "qwerty.asdfg@mail.com";
        String validEmail3 = "disposable.style.email.with+symbol@example.com";
        String validEmail4 = "disposable.style.email.with+symbol@example.com";
        String validEmail5 = "fully-qualified-domain@example.com";
        String validEmail6 = "example-indeed@strange-example.com";
        String validEmail7 = "test/test@test.com";
        String validEmail8 = "admin@mailserver1";
        String validEmail9 = "mailhost!username@example.org";
        String validEmail10 = "user%example.com@example.org";
        String validEmail11 = "user-@example.org";
        // Then
        assertTrue(validationUtility.isValidEmail(validEmail1));
        assertTrue(validationUtility.isValidEmail(validEmail2));
        assertTrue(validationUtility.isValidEmail(validEmail3));
        assertTrue(validationUtility.isValidEmail(validEmail4));
        assertTrue(validationUtility.isValidEmail(validEmail5));
        assertTrue(validationUtility.isValidEmail(validEmail6));
        assertTrue(validationUtility.isValidEmail(validEmail7));
        assertTrue(validationUtility.isValidEmail(validEmail8));
        assertTrue(validationUtility.isValidEmail(validEmail9));
        assertTrue(validationUtility.isValidEmail(validEmail10));
        assertTrue(validationUtility.isValidEmail(validEmail11));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnFalse_whenBlankOrNotCapitalLettersUsed() {
        // Given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String invalidString1 = "aaaa";
        String invalidString2 = "AaAaA";
        String invalidString3 = "AA!@#$%^&*";
        String invalidString4 = "___";
        // Then
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(nullString));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(emptyString));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(stringWithSpaces));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString1));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString2));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString3));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString4));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnFalse_whenStartsOrEndsWithUnderscoresOrHasSequenceOfThem() {
        // Given
        String invalidString5 = "_AA_A";
        String invalidString6 = "A_AA_";
        String invalidString7 = "AA_______A";
        // Then
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString5));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString6));
        assertFalse(validationUtility.isValidStrCapitalAndUnderscoresOnly(invalidString7));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnTrue_whenHasUnderscoresBetweenCapitals() {
        // Given
        String validString1 = "AAAA";
        String validString2 = "ABC_ABC";
        String validString3 = "ABC_ABC_A";
        String validString4 = "A_ABC_A";
        // Then
        assertTrue(validationUtility.isValidStrCapitalAndUnderscoresOnly(validString1));
        assertTrue(validationUtility.isValidStrCapitalAndUnderscoresOnly(validString2));
        assertTrue(validationUtility.isValidStrCapitalAndUnderscoresOnly(validString3));
        assertTrue(validationUtility.isValidStrCapitalAndUnderscoresOnly(validString4));
    }

    @Test
    void isValidUsernameOrPassword_shouldReturnFalse_whenHasForbiddenSymbolsOrShorterThen4Symbols() {
        // Given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String invalidString1 = "A";
        String invalidString2 = "Aa";
        String invalidString3 = "Aaa";
        String invalidString4 = "qwerty!@#%^&";
        // Then
        assertFalse(validationUtility.isValidUsernameOrPassword(nullString));
        assertFalse(validationUtility.isValidUsernameOrPassword(emptyString));
        assertFalse(validationUtility.isValidUsernameOrPassword(stringWithSpaces));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString1));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString2));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString3));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString4));
    }

    @Test
    void isValidUsernameOrPassword_shouldReturnFalse_whenStartsNotWithALetter() {
        // Given
        String invalidString1 = "_qwerty";
        String invalidString2 = ".qwerty";
        String invalidString3 = "-qwerty";
        String invalidString4 = "123qwerty";
        String invalidString5 = "1234";
        // Then
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString1));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString2));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString3));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString4));
        assertFalse(validationUtility.isValidUsernameOrPassword(invalidString5));
    }

    @Test
    void isValidBooleanRepresentedWithInteger_shouldReturnFalse_whenNullOrRandomNumber() {
        // Given
        Integer invalid1 = null;
        Integer invalid2 = -5;
        Integer invalid3 = 5;
        // Then
        assertFalse(validationUtility.isValidBooleanRepresentedWithInteger(invalid1));
        assertFalse(validationUtility.isValidBooleanRepresentedWithInteger(invalid2));
        assertFalse(validationUtility.isValidBooleanRepresentedWithInteger(invalid3));
    }

    @Test
    void isValidBooleanRepresentedWithInteger_shouldReturnTrue_whenDefaultFlagsUsed() {
        // Given
        Integer valid1 = ApplicationConstants.DEFAULT_FALSE_FLAG;
        Integer valid2 = ApplicationConstants.DEFAULT_TRUE_FLAG;
        // Then
        assertTrue(validationUtility.isValidBooleanRepresentedWithInteger(valid1));
        assertTrue(validationUtility.isValidBooleanRepresentedWithInteger(valid2));
    }
}