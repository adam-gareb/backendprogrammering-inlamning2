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

    @Around("execution(* se.yrgo.dataaccess.*.*(..)) || execution(* se.yrgo.services.*.*(..))")
    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            Object value = method.proceed();
            return value;
        } finally {
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println("Metoden " +
                    method.getSignature().getName() +
                    " tog " + timeTaken + " ms");
        }
    }
}