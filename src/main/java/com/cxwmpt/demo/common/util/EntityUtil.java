package com.cxwmpt.demo.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author Administrator
 */
public class EntityUtil {
    /**
     *
     * @param source 被复制的实体类对象
     * @param to 复制完后的实体类对象
     * @throws Exception
     */
    public static void copy(Object source, Object to) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(), Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < sourceProperty.length; i++) {
                for (int j = 0; j < destProperty.length; j++) {
                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

}
