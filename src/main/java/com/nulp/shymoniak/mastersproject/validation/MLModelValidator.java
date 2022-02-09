package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.MLModelObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MLModelValidator implements Validator<MLModelDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public MLModelValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(MLModelDTO mlModelDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(mlModelDTO.getModelName())
                || generalValidationUtility.isValidURL(mlModelDTO.getModelPath())
                || validateObservedObjectList(mlModelDTO.getObservedObjectList());
    }

    // TODO: 2/9/22 add list of objects validation (ObservedObjectValidator)
    private boolean validateObservedObjectList(List<MLModelObservedObjectDTO> mlModelObservedObjectList){
        return true;
    }
}
