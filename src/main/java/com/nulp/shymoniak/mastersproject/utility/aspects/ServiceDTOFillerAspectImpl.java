package com.nulp.shymoniak.mastersproject.utility.aspects;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceDTOFillerAspectImpl implements ServiceDTOFillerAspect {
    private final AspectUtility aspectUtility;

    @Override
    @SneakyThrows
    @Around("callCreateMethod()")
    public void fillDTOFieldsOnCreate(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fillCreateFields(dtoEntity);
        proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }

    @Override
    @SneakyThrows
    @Around("callUpdateMethod() || callDeleteMethod()")
    public void fillDTOFieldsOnUpdateOrDelete(ProceedingJoinPoint proceedingJoinPoint) {
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(proceedingJoinPoint);
        dtoEntity = fillUpdateFields(dtoEntity);
        proceedingJoinPoint.proceed(new Object[]{dtoEntity});
    }

    @SneakyThrows
    private Object fillCreateFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("createDate")) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            } else if (field.getName().equals("createUser")) {
                // TODO: 2/8/22  set user from session
            }
        }
        return obj;
    }

    @SneakyThrows
    private Object fillUpdateFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("updateDate")) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            } else if (field.getName().equals("updateUser")) {
                // TODO: 2/8/22  set user from session
            }
        }
        return obj;
    }
}
