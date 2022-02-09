package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator implements Validator<PersonDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public PersonValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(PersonDTO personDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(personDTO.getName())
                && generalValidationUtility.isNotNullAndNotBlank(personDTO.getSurname())
                && generalValidationUtility.isNotNullAndNotBlank(personDTO.getEmail());

    }
}
