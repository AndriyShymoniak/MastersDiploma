package com.nulp.shymoniak.mastersproject.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public interface ServiceDTOFillerAspect {

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service*..*create*(..))")
    default void callCreateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service*..*update*(..))")
    default void callUpdateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service*..*delete*(..))")
    default void callDeleteMethod() {
    }

    Object fillDTOFieldsOnCreate(ProceedingJoinPoint proceedingJoinPoint);

    Object fillDTOFieldsOnUpdateOrDelete(ProceedingJoinPoint proceedingJoinPoint);
}
