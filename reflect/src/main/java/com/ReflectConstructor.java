package com;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReflectConstructor {

    @SneakyThrows
    public static void main(String[] args) {
        // 反射执行构造方法
        //Integer instance = Integer.class.newInstance();


//        Class<Integer> cls = Integer.class;
//        Constructor<Integer> constructor = cls.getDeclaredConstructor();
//        Integer integer = constructor.newInstance(20);
//
//        Integer integer1 = constructor.newInstance(10);
//        System.out.println(integer);
//        System.out.println(integer1);
        
        System.out.println("-------->获取继承关系<----------");
        Class<Student> clazz = Student.class;
        // 不带泛型
        Class<? super Student> superclass = clazz.getSuperclass();
        System.out.println("superclass = " + superclass);
        // 带泛型参数
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println("genericSuperclass = " + genericSuperclass);

        Class<?>[] interfaces = clazz.getInterfaces();
        Type[] types = clazz.getGenericInterfaces();

        System.out.println("-------->获取接口参数<----------");
        Stream.concat(Arrays.stream(interfaces), Arrays.stream(types))
                .forEach(System.out::println);

        System.out.println("-------->获取向上转型是否能够成功<----------");
        System.out.println(Number.class.isAssignableFrom(Number.class));
        System.out.println(String.class.isAssignableFrom(Number.class));

    }

    static class Student extends Person<Student> implements Serializable, Cloneable, Closeable, Comparable<Student> {

        @Override
        public void close() throws IOException {
            // todo 如果申请了底层资源, 处理优雅关闭逻辑
        }

        @Override
        public int compareTo(Student o) {
            return 0;
        }
    }

    static class Person<T> {

    }
 }


