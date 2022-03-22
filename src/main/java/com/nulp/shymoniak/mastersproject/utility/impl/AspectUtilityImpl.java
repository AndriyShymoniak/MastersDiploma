package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AspectUtilityImpl implements AspectUtility {
    private final ApplicationContext context;

    /**
     * Gets instance of class where current JoinPoint is used from ApplicationContext
     *
     * @param joinPoint
     * @return an instance of class where JoinPoint occurs
     */
    @Override
    public Object getInstanceOfClassWithJoinPoint(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Class targetClass = signature.getDeclaringType();
        return context.getBean(targetClass);
    }


    /**
     * Gets instance of DTO class from JoinPoint arguments
     *
     * @param joinPoint
     * @return argument of JoinPoint - instance of DTO class
     * @throws ApiRequestException if no DTO argument is provided
     */
    @Override
    public Object getDTOEntityFromParameters(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        for (Object arg : arguments) {
            if (arg.getClass().getSimpleName().endsWith("DTO")) {
                return arg;
            }
        }
        throw new ApiRequestException("No DTO argument is provided");
    }
}
