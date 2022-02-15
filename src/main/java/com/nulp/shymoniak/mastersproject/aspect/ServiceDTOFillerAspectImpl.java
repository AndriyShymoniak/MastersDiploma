package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceDTOFillerAspectImpl implements ServiceDTOFillerAspect {
    private final AspectUtility aspectUtility;
    private final DTOFieldFiller fieldFiller;

    @Override
    @SneakyThrows
    @Around("callCreateMethod()")
    public void fillDTOFieldsOnCreate(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fieldFiller.fillCreateFields(dtoEntity);
        proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }

    @Override
    @SneakyThrows
    @Around("callUpdateMethod() || callDeleteMethod()")
    public void fillDTOFieldsOnUpdateOrDelete(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fieldFiller.fillUpdateFields(dtoEntity);
        proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }
}
