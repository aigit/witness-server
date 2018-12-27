package com.caiyuna.witness.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要加锁的参数
 * @author Ldl
 * @date 2018/11/8 15:30
 * @since 1.0.0
 */
@Target({ ElementType.PARAMETER,ElementType.METHOD,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockParam {

    /**
     * 要加锁的对象名称
     * @return
     */
    String name() default "";
}
