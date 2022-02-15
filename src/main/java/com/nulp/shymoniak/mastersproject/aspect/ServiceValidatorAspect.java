package com.nulp.shymoniak.mastersproject.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public interface ServiceValidatorAspect {

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*create*(..))")
    default void callCreateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*update*(..))")
    default void callUpdateMethod() {
    }

    void validateDTOs(JoinPoint joinPoint);
}
