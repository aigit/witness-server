package com.caiyuna.witness.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Ldl
 * @date 2018/11/8 15:40
 * @since 1.0.0
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数，生成指定缓存key
     * @param pjp
     * @return 缓存key
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
