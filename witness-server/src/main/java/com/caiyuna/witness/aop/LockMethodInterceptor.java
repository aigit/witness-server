package com.caiyuna.witness.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.caiyuna.witness.redis.RedisService;
import com.caiyuna.witness.service.CacheKeyGenerator;
import com.caiyuna.witness.service.impl.LockKeyGeneratorImpl;

/**
 * @author Ldl
 * @date 2018/11/8 16:51
 * @since 1.0.0
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    @Autowired
    private RedisService redisService;
    @Autowired
    private CacheKeyGenerator cacheKeyGenerator;


}
