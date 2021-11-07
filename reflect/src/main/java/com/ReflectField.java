package com;

import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectField {

    public static void main(String[] args) {
        // printField(Student.class);
        // langClassField(String.class);
        Object obj = new Person();
        getSetValue(obj);

    }

    @SneakyThrows
    public static void getSetValue(Object obj) {
        Class<?> clazz = obj.getClass();
        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");
        // 反射读取值, 设置值, 私有字段要打开访问控制, 解释器模式不允许直接访问私有字段
        name.setAccessible(true);
        age.setAccessible(true);
        Object nameValue = name.get(obj);
        Object ageValue = age.get(obj);
        System.out.println("nameValue = " + nameValue);
        System.out.println("ageValue = " + ageValue);

        name.set(obj, "tom");
        age.set(obj, 18);
        System.out.println(obj);


    }

    @SneakyThrows
    public static void printField(Class<?> clazz) {
        System.out.println("-------->fields 读取public字段, 包括父类public字段<----------");
        Field[] fs = clazz.getFields();
        Arrays.stream(fs).forEach(f -> {
            int mod = f.getModifiers();
            String typeName = f.getType().getName();
            String fieldName = f.getName();
            System.out.printf("%s %s %s%n", Modifier.toString(mod), typeName, fieldName);
        });

        System.out.println("-------->declaredField 读取类本身所有声明的字段, 不包含父类字段<----------");

        Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f -> {
            int mod = f.getModifiers();
            String typeName = f.getType().getName();
            String fieldName = f.getName();
            System.out.printf("%s %s %s%n", Modifier.toString(mod), typeName, fieldName);
        });

        System.out.println("-------->getField<----------");
        // 包含父类字段
        Field ageField = clazz.getField("age");
        // Field scoreField = clazz.getField("grade");
        // 不包含父类字段
        Field scoreField = clazz.getDeclaredField("grade");
        System.out.println(ageField);
        System.out.println(scoreField);
    }

    @SneakyThrows
    public static void langClassField(Class<?> clazz) {
        Field value = clazz.getDeclaredField("value");
        Field coder = clazz.getDeclaredField("coder");
        Field hash = clazz.getDeclaredField("hash");

        System.out.println(value.getName());
        System.out.println(value.getType());

        int mod = value.getModifiers();
        System.out.println(Modifier.isPublic(mod));
        System.out.println(Modifier.isPrivate(mod));
        System.out.println(Modifier.isFinal(mod));
        System.out.println(Modifier.isTransient(mod));

    }

    static class Student extends Person {
        public int score;
        private int grade;
    }

    @ToString
    static class Person {
        private String name;
        public Integer age;


    }

}
