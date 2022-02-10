package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.MLModelObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MLModelValidator implements Validator<MLModelDTO> {
    private final ValidationUtility validationUtility;
    private final ObservedObjectValidator observedObjectValidator;

    @Autowired
    public MLModelValidator(ValidationUtility validationUtility, ObservedObjectValidator observedObjectValidator) {
        this.validationUtility = validationUtility;
        this.observedObjectValidator = observedObjectValidator;
    }

    @Override
    public boolean isValid(MLModelDTO mlModelDTO) {
        return validationUtility.isNotNullAndNotBlank(mlModelDTO.getModelName())
                && validationUtility.isValidURL(mlModelDTO.getModelPath())
                && isObservedObjectListValid(mlModelDTO.getObservedObjectList());
    }

    private boolean isObservedObjectListValid(List<MLModelObservedObjectDTO> mlModelObservedObjectList){
        return mlModelObservedObjectList.stream()
                .map(mlModelObservedObject -> mlModelObservedObject.getObservedObject())
                .allMatch(item -> observedObjectValidator.isValid(item));
    }
}
