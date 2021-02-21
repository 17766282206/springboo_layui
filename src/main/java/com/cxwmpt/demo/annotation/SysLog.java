package com.cxwmpt.demo.annotation;

import java.lang.annotation.*;

/**
 * @program: backend
 * @description 系统日志注解，方法功能描述
 * @author: zhoudi
 * @create: 2020-04-03 09:53
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
