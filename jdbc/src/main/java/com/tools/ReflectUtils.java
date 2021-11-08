package com.tools;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Field;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class ReflectUtils {


    /**
     * 反射为实例的字段赋值
     * @param t 实例
     * @param fieldName 实例字段名
     * @param fieldValue 字段值
     * @param <T> 目标类型
     */
    public static <T> void setValue(T t, String fieldName, Object fieldValue) {
        Class<?> clazz = t.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.set(t, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
