package com.cxwmpt.demo.annotation;

import java.lang.annotation.*;

/**
 * 数据字典注解
 * @program: backend
 * @description 系统日志注解，方法功能描述
 * @author: zhoudi
 * @create: 2020-04-03 09:53
 **/
@Target(value = {ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})//该注解可以放在方法上，属性上，类上
@Retention(RetentionPolicy.RUNTIME)//运行环境下
public @interface SysDict {
    /**
     * 字典类型
     *
     * @return
     */
    String dictCode();

    /**
     * 返回属性名
     *
     * @return
     */
    String dictText() default "";
}