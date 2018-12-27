/**
 * 
 */
package com.caiyuna.witness.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.caiyuna.witness.controller..*.*(..))")
    public void weblog() {
    }

    @Before("weblog()")
    public void deBefore(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();

        System.out.println(String.format("URL:{},HTTP_METHOD:{},IP:{},CLASS_METHOD:{},Args:{}", request.getRequestURL().toString(),
                                         request.getMethod(), request.getRemoteAddr(),
                                         joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                                         Arrays.toString(joinPoint.getArgs())));
    }

    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doAfterReturning(Object ret) throws Exception {
        System.out.println("方法的返回值:" + ret);
    }

    @AfterThrowing("weblog()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法抛出异常时执行。。。。");
    }

    @After("weblog()")
    public void after(JoinPoint jp) {
        System.out.println("方法最后 执行");
    }

    @Around("weblog()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("方法环绕start。。。。。");
        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("方法环绕proceed,结果是:" + o);
        return o;
    }

}
