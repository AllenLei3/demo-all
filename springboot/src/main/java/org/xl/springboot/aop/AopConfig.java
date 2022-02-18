package org.xl.springboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xulei
 */
@Aspect
@Component
public class AopConfig {

    /**
     * 引擎内部
     */
    @Pointcut("execution(* org.xl.springboot.aop..*(..)) && execution(* say(..))")
    private void innerStep() {}

    /**
     * 外部扩展
     */
    @Pointcut("@annotation(org.xl.springboot.aop.AopAnnotation)")
    private void extensionStep() {}

    @Resource
    private AopTest aopTest;

    @Around("innerStep() || extensionStep()")
    public Object innerRiskExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        return riskExecute(joinPoint);
    }

    @Around("extensionStep()")
    public Object extensionRiskExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        return riskExecute(joinPoint);
    }

    public Object riskExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before to invoke Test");
        Object result = joinPoint.proceed();
        System.out.println("After to invoke Test");
        return result;
    }
}
