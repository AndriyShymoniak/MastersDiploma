package com.nulp.shymoniak.mastersproject.utility.aspects;

import org.aspectj.lang.JoinPoint;

public interface AspectUtility {
    Object getInstanceOfClassWithJoinPoint(JoinPoint joinPoint);

    Object getDTOEntityFromParameters(JoinPoint joinPoint);
}
