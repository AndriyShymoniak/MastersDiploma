package com.nulp.shymoniak.mastersproject.utility.aspect;

import org.aspectj.lang.JoinPoint;

public interface AspectUtility {
    Object getInstanceOfClassWithJoinPoint(JoinPoint joinPoint);

    Object getDTOEntityFromParameters(JoinPoint joinPoint);
}
