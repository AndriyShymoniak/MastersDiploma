package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceValidatorAspectImpl implements ServiceValidatorAspect{
    private final AspectUtility aspectUtility;

    /**
     * Validates DTO classes instances in ServiceImpl classes
     * before create and update methods are called
     * @param joinPoint
     */
    @Override
    @SneakyThrows
    @Before("callCreateMethod() || callUpdateMethod()")
    public void validateDTOs(JoinPoint joinPoint) {
        Object serviceImplInstance = aspectUtility.getInstanceOfClassWithJoinPoint(joinPoint);
        Object dtoEntity = aspectUtility.getDTOEntityFromParameters(joinPoint);
        Method checkIfValid;
        try {
            checkIfValid = serviceImplInstance.getClass()
                    .getDeclaredMethod("checkIfValid", Object.class);
        } catch (NoSuchMethodException ex) {
            checkIfValid = serviceImplInstance.getClass().getSuperclass()
                    .getDeclaredMethod("checkIfValid", Object.class);
        }
        checkIfValid.invoke(serviceImplInstance, dtoEntity);
    }
}
