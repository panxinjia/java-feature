package com;

import lombok.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReflectMethod {

    @SneakyThrows
    public static void main(String[] args) {
        Class<Student> clazz = Student.class;
        Method[] ms = clazz.getDeclaredMethods();
        // 获取字节码方法的全部信息
        Arrays.stream(ms)
                .forEach(method -> {
                    int mod = method.getModifiers();
                    Class<?> returnType = method.getReturnType();
                    String name = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    String parameter = Arrays.stream(parameterTypes)
                            .map(Class::getName)
                            .collect(Collectors.joining(",", "(", ")"));
                    // 方法名称, 修饰符  返回值类型, 方法名, 参数类型
                    System.out.printf("%s %s %s %s%n", Modifier.toString(mod), returnType.getName(), name,
                            parameter);
                });

        // 调用方法
        System.out.println("-------->callMethod<----------");

        Student student = Student.builder().id(1001).age(20).name("jerry").build();

        Method getInfo = clazz.getDeclaredMethod("getInfo");
        Object result = getInfo.invoke(student);
        if (Objects.nonNull(result))
            System.out.println(result);

        Method printInfo = clazz.getDeclaredMethod("printInfo", Student.class);
        Object invoke = printInfo.invoke(student, student);
        if (Objects.nonNull(invoke))
            System.out.println(invoke);

        Method setInfo = clazz.getDeclaredMethod("setInfo", int.class, String.class, Integer.class);
        // 核心类库字段的setAccessible访问可能会失败, 基于对核心类库的保护
        setInfo.setAccessible(true);
        result = setInfo.invoke(student, 100, "tom", Integer.MAX_VALUE);
        if (Objects.nonNull(result))
            System.out.println(result);

        System.out.println(student);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student {

        private int id;
        private String name;
        private Integer age;

        public String getInfo() {
            return String.format("%d - %s - %d", id, name, age);
        }

        public static String printInfo(Student s) {
            return String.format("static method printInfo ( %d - %s - %d )", s.getId(), s.getName(), s.getAge());
        }

        private void setInfo(int id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

    }
}
