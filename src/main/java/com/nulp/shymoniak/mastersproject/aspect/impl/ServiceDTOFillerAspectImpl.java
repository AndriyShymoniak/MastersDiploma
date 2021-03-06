package com.nulp.shymoniak.mastersproject.aspect.impl;

import com.nulp.shymoniak.mastersproject.aspect.ServiceDTOFillerAspect;
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
    public Object fillDTOFieldsOnCreate(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fieldFiller.fillCreateFields(dtoEntity);
        return proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }

    // TODO: 3/15/22 make a separate method for delete or change arguments in deleteItem()
    @Override
    @SneakyThrows
    @Around("callUpdateMethod()")
    public Object fillDTOFieldsOnUpdateOrDelete(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fieldFiller.fillUpdateFields(dtoEntity);
        return proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }
}
