package com.caiyuna.witness.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.caiyuna.witness.annotation.CacheLock;
import com.caiyuna.witness.annotation.LockParam;
import com.caiyuna.witness.service.CacheKeyGenerator;

/**
 * 生成缓存Key
 * @author Ldl
 * @date 2018/11/8 15:59
 * @since 1.0.0
 */
public class LockKeyGeneratorImpl implements CacheKeyGenerator {

    /**
     * 扫描注解参数 生成缓存Key
     * @param pjp
     * @return
     */
    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();

        StringBuilder builder = new StringBuilder();
        for (int i=0;i<parameters.length;i++){
            final LockParam lockParamAnnotation = parameters[i].getAnnotation(LockParam.class);
            if(lockAnnotation==null){
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString())){
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i=0;i<parameterAnnotations.length;i++){
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field:fields){
                    final LockParam annotation = field.getAnnotation(LockParam.class);
                    if (annotation==null){
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field,object));
                }
            }
        }
        return lockAnnotation.prefix()+builder.toString();
    }
}
