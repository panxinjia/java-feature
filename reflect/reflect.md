# 反射

> 静态语言中的**动态性**.

## Class类



## 访问字段



## 调用方法



## 调用构造方法



## 读取继承关系



## 动态代理



# 内省

> 基于反射的, 一套处理JavaBean的API

```java
package com.introspection;

import com.bean.Person;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception{
        // 读取Bean信息, 处理JavaBean
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);

        // 读取Bean的描述信息
        BeanDescriptor bd = beanInfo.getBeanDescriptor();
        processBeanDescriptor(bd);

        // 读取属性描述信息
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        // Arrays.stream(pds).forEach(Main::processPropertyDescriptor);

        // 读取方法描述信息
        MethodDescriptor[] mds = beanInfo.getMethodDescriptors();
        // Arrays.stream(mds).forEach(Main::processMethodDescriptor);
    }

    private static void processBeanDescriptor(BeanDescriptor bd) {
        Class<?> beanClass = bd.getBeanClass();
        Class<?> customizerClass = bd.getCustomizerClass();

        System.out.println("beanClass = " + beanClass);
        System.out.println("customizerClass = " + customizerClass);
    }

    private static void processPropertyDescriptor(PropertyDescriptor pd) {
        System.out.println("-------->属性描述符<----------");
        // 属性getter方法
        Method getter = pd.getReadMethod();

        // setter方法
        Method setter = pd.getWriteMethod();

        // 属性类型
        Class<?> propertyType = pd.getPropertyType();
        System.out.println("typeName=" + propertyType.getName());

        String name = pd.getName();
        System.out.println("name = " + name);
        String displayName = pd.getDisplayName();
        System.out.println("displayName = " + displayName);

        Object value = pd.getValue("name");
        System.out.println("value = " + value);

        String shortDescription = pd.getShortDescription();
        System.out.println("shortDescription = " + shortDescription);

    }
    private static void processMethodDescriptor(MethodDescriptor md) {
        // JavaBean方法的参数描述符, 处理参数api
        ParameterDescriptor[] parameterDescriptors = md.getParameterDescriptors();
        Method method = md.getMethod();
    }
}

```

