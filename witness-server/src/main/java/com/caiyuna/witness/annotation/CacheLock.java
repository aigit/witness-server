package com.caiyuna.witness.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author Ldl
 * @date 2018/11/8 15:25
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {
    /**
     * 锁前缀
     * @return redis锁的前缀
     */
    String prefix() default "";

    /**
     * 过期秒数，默认为5秒
     * @return 过期秒数
     */
    int expire() default 5;

    /**
     * 过期时间单位
     * @return 过期时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Key的分隔符
     * @return
     */
    String delimiter() default ":";
}
