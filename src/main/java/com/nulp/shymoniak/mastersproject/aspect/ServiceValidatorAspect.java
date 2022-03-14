package com.nulp.shymoniak.mastersproject.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public interface ServiceValidatorAspect {

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service*..*create*(..))")
    default void callCreateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service*..*update*(..))")
    default void callUpdateMethod() {
    }

    void validateDTOs(JoinPoint joinPoint);
}
