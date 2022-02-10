package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator implements Validator<PersonDTO> {
    private final ValidationUtility validationUtility;

    @Autowired
    public PersonValidator(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    // TODO: 2/9/22 add email validation
    // TODO: 2/9/22 add validation on name and surname with letters only (no *&^%$)
    @Override
    public boolean isValid(PersonDTO personDTO) {
        return validationUtility.isNotNullAndNotBlank(personDTO.getName())
                && validationUtility.isNotNullAndNotBlank(personDTO.getSurname())
                && validationUtility.isNotNullAndNotBlank(personDTO.getEmail());
    }
}
