package com.nulp.shymoniak.mastersproject.utility;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.utility.validation.ValidationUtilityImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationUtilityImplTest {
    private final ValidationUtilityImpl validationUtilityImpl;

    @Autowired
    public ValidationUtilityImplTest(ValidationUtilityImpl validationUtilityImpl) {
        this.validationUtilityImpl = validationUtilityImpl;
    }

    @Test
    void isNotNullAndNotBlank_shouldReturnFalse_whenStringIsBlank() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        // then
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(nullString));
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(emptyString));
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(stringWithSpaces));
    }

    @Test
    void isNotNullAndNotBlank_shouldReturnTrue_whenStringIsNotBlank() {
        // given
        String validString1 = "s";
        String validString2 = "A random string";
        // then
        assertTrue(validationUtilityImpl.isNotNullAndNotBlank(validString1));
        assertTrue(validationUtilityImpl.isNotNullAndNotBlank(validString2));
    }

    @Test
    void isValidURL_shouldReturnFalse_whenBlankRandomOrDoesNotMeetStandards() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String randomString = "A random string";
        String invalidURL1 = "https://";
        String invalidURL2 = "github.com/";
        // then
        assertFalse(validationUtilityImpl.isValidURL(nullString));
        assertFalse(validationUtilityImpl.isValidURL(emptyString));
        assertFalse(validationUtilityImpl.isValidURL(stringWithSpaces));
        assertFalse(validationUtilityImpl.isValidURL(randomString));
        assertFalse(validationUtilityImpl.isValidURL(invalidURL1));
        assertFalse(validationUtilityImpl.isValidURL(invalidURL2));
    }

    @Test
    void isValidURL_shouldReturnTrue_whenMeetsStandards() {
        // given
        String validUrl1 = "https://github.com/";
        String validUrl2 = "https://shorturl.at/elnI5";
        String validUrl3 = "https://helpx.adobe.com/photoshop/using/convert-color-image-black-white.html";
        // then
        assertTrue(validationUtilityImpl.isValidURL(validUrl1));
        assertTrue(validationUtilityImpl.isValidURL(validUrl2));
        assertTrue(validationUtilityImpl.isValidURL(validUrl3));
    }

    @Test
    void isValidEmail_shouldReturnFalse_whenBlankRandomOrDoesNotMeetStandards() {
        // given
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
        // then
        assertFalse(validationUtilityImpl.isValidEmail(nullString));
        assertFalse(validationUtilityImpl.isValidEmail(emptyString));
        assertFalse(validationUtilityImpl.isValidEmail(stringWithSpaces));
        assertFalse(validationUtilityImpl.isValidEmail(randomString));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail1));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail2));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail3));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail4));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail5));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail6));
        assertFalse(validationUtilityImpl.isValidEmail(invalidEmail7));
    }

    @Test
    void isValidEmail_shouldReturnTrue_whenMeetsStandards(){
        // given
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
        // then
        assertTrue(validationUtilityImpl.isValidEmail(validEmail1));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail2));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail3));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail4));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail5));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail6));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail7));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail8));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail9));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail10));
        assertTrue(validationUtilityImpl.isValidEmail(validEmail11));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnFalse_whenBlankOrNotCapitalLettersUsed() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String invalidString1 = "aaaa";
        String invalidString2 = "AaAaA";
        String invalidString3 = "AA!@#$%^&*";
        String invalidString4 = "___";
        // then
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(nullString));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(emptyString));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(stringWithSpaces));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString1));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString2));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString3));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString4));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnFalse_whenStartsOrEndsWithUnderscoresOrHasSequenceOfThem() {
        // given
        String invalidString5 = "_AA_A";
        String invalidString6 = "A_AA_";
        String invalidString7 = "AA_______A";
        // then
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString5));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString6));
        assertFalse(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(invalidString7));
    }

    @Test
    void isValidStrCapitalAndUnderscoresOnly_shouldReturnTrue_WhenHasUnderscoresBetweenCapitals() {
        // given
        String validString1 = "AAAA";
        String validString2 = "ABC_ABC";
        String validString3 = "ABC_ABC_A";
        String validString4 = "A_ABC_A";
        // then
        assertTrue(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(validString1));
        assertTrue(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(validString2));
        assertTrue(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(validString3));
        assertTrue(validationUtilityImpl.isValidStrCapitalAndUnderscoresOnly(validString4));
    }

    @Test
    void isValidBooleanRepresentedWithInteger_shouldReturnFalse_whenNullOrRandomNumber(){
        // given
        Integer invalid1 = null;
        Integer invalid2 = -5;
        Integer invalid3 = 5;
        // then
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid1));
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid2));
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid3));
    }

    @Test
    void isValidBooleanRepresentedWithInteger_shouldReturnTrue_whenDefaultFlagsUsed(){
        // given
        Integer valid1 = ApplicationConstants.DEFAULT_FALSE_FLAG;
        Integer valid2 = ApplicationConstants.DEFAULT_TRUE_FLAG;
        // then
        assertTrue(validationUtilityImpl.isValidBooleanRepresentedWithInteger(valid1));
        assertTrue(validationUtilityImpl.isValidBooleanRepresentedWithInteger(valid2));
    }
}