package com.nulp.shymoniak.mastersproject.utility;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ServiceAspectUtility {
    private final ApplicationContext context;

    @Autowired
    public ServiceAspectUtility(ApplicationContext context) {
        this.context = context;
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*create*(..))")
    public void callCreateMethod() {
    }

    @Pointcut("execution(* com.nulp.shymoniak.mastersproject.service.impl*..*update*(..))")
    public void callUpdateMethod() {
    }

    /**
     * Validates DTO classes instances in ServiceImpl classes
     * before create and update methods are called
     * @param joinPoint
     */
    @SneakyThrows
    @Before("callCreateMethod() || callUpdateMethod()")
    public void validateDTOs(JoinPoint joinPoint) {
        Object serviceImplInstance = getInstanceOfClassWithJoinPoint(joinPoint);
        Object dtoEntity = getDTOEntityFromParameters(joinPoint);
        Method checkIfValid = serviceImplInstance.getClass().getDeclaredMethod("checkIfValid", Object.class);
        checkIfValid.invoke(serviceImplInstance, dtoEntity);
    }

    /**
     * Gets instance of class where current JoinPoint is used from ApplicationContext
     * @param joinPoint
     * @return an instance of class where JoinPoint occurs
     */
    private Object getInstanceOfClassWithJoinPoint(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Class targetClass = signature.getDeclaringType();   // Get ServiceImpl class
        return context.getBean(targetClass);                // Get ServiceImpl class instance
    }

    /**
     * Gets instance of DTO class from JoinPoint arguments
     * @param joinPoint
     * @return argument of JoinPoint - instance of DTO class
     * @throws ApiRequestException if no DTO argument is provided
     */
    private Object getDTOEntityFromParameters(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        for (Object arg : arguments) {
            if (arg.getClass().getSimpleName().endsWith("DTO")) {
                return arg;
            }
        }
        throw new ApiRequestException("No DTO argument is provided");
    }
}
