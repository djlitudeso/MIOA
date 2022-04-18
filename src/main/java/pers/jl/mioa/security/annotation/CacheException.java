package pers.jl.mioa.security.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，有该注解的缓存方法会抛出异常
 *
 * @author JL
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
