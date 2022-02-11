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
    void checkIfStringIsNotNullNotBlank() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String validString = "A random string";
        // then
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(nullString));
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(emptyString));
        assertFalse(validationUtilityImpl.isNotNullAndNotBlank(stringWithSpaces));
        assertTrue(validationUtilityImpl.isNotNullAndNotBlank(validString));
    }

    @Test
    void checkIfURLIsValid() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String randomString = "A random string";
        String invalidURL1 = "https://";
        String invalidURL2 = "github.com/";
        String validUrl = "https://github.com/";
        // then
        assertFalse(validationUtilityImpl.isValidURL(nullString));
        assertFalse(validationUtilityImpl.isValidURL(emptyString));
        assertFalse(validationUtilityImpl.isValidURL(stringWithSpaces));
        assertFalse(validationUtilityImpl.isValidURL(randomString));
        assertFalse(validationUtilityImpl.isValidURL(invalidURL1));
        assertFalse(validationUtilityImpl.isValidURL(invalidURL2));
        assertTrue(validationUtilityImpl.isValidURL(validUrl));
    }

    @Test
    void checkingInvalidEmails() {
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
    void checkingValidEmails(){
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
    void checkIfStringContainsCapitalLettersAndUnderscoreOnly() {
        // given
        String nullString = null;
        String emptyString = "";
        String stringWithSpaces = "   ";
        String invalidString1 = "aaaa";
        String invalidString2 = "AaAaA";
        String invalidString3 = "AA!@#$%^&*";
        String invalidString4 = "___";
        String invalidString5 = "_AA_A";
        String invalidString6 = "A_AA_";
        String invalidString7 = "AA_______A";
        String validString1 = "AAAA";
        String validString2 = "ABC_ABC";
        String validString3 = "ABC_ABC_A";
        String validString4 = "A_ABC_A";
        // then
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(nullString));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(emptyString));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(stringWithSpaces));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString1));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString2));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString3));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString4));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString5));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString6));
        assertFalse(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(invalidString7));
        assertTrue(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(validString1));
        assertTrue(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(validString2));
        assertTrue(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(validString3));
        assertTrue(validationUtilityImpl.isValidStringWithCapitalLettersAndUnderscores(validString4));
    }

    @Test
    void checkIfBooleanIsRepresentedWithIntegerCorrectly(){
        // given
        Integer invalid1 = null;
        Integer invalid2 = -5;
        Integer invalid3 = 5;
        Integer valid1 = ApplicationConstants.DEFAULT_FALSE_FLAG;
        Integer valid2 = ApplicationConstants.DEFAULT_TRUE_FLAG;
        // then
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid1));
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid2));
        assertFalse(validationUtilityImpl.isValidBooleanRepresentedWithInteger(invalid3));
        assertTrue(validationUtilityImpl.isValidBooleanRepresentedWithInteger(valid1));
        assertTrue(validationUtilityImpl.isValidBooleanRepresentedWithInteger(valid2));
    }
}