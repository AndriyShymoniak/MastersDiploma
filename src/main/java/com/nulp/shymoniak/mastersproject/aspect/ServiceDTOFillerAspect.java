package com.nulp.shymoniak.mastersproject.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public interface ServiceDTOFillerAspect {

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*create*(..))")
    default void callCreateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*update*(..))")
    default void callUpdateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*delete*(..))")
    default void callDeleteMethod() {
    }

    void fillDTOFieldsOnCreate(ProceedingJoinPoint proceedingJoinPoint);

    void fillDTOFieldsOnUpdateOrDelete(ProceedingJoinPoint proceedingJoinPoint);
}
