package se.yrgo.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {

    @Pointcut("execution(* se.yrgo.services..*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* se.yrgo.dataaccess..*(..))")
    public void dataAccessMethods() {}

    @Pointcut("serviceMethods() || dataAccessMethods()")
    public void allMethods() {}

    @Around("allMethods()")
    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {
        long startTime = System.nanoTime();

        try {
            Object value = method.proceed();
            return value;
        } finally {
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("Metoden " +
                    method.getSignature().getName() +
                    " tog " + timeTaken / 1000000 + " ms");
        }
    }

    @Before("allMethods()")
    public void beforeAdviceTesting(JoinPoint jp) {
        System.out.println("Anropar metoden: " + jp.getSignature().getName());
    }
}