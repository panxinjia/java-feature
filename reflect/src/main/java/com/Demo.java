package com;

import com.bean.Person;
import org.junit.jupiter.api.Test;

public class Demo {

    @Test
    public void createClassInstance() throws ClassNotFoundException{
        Person person = new Person();

        // 方式一
        Class<Person> clazz1 = Person.class;


        // 方式二
        Class<? extends Person> clazz2 = person.getClass();

        // 方式三
        Class<?> clazz3 = Class.forName("com.bean.Person");// java.lang.ClassNotFoundException java.class.path路径中没有找到对应的字节码

        // 方式四
        Class<?> clazz4 = ClassLoader.getSystemClassLoader().loadClass("com.bean.Person");

    }

    @Test
    public void testClass() {
        Class<String> clazz = String.class;
        Class<? extends String> clazz2 = "hello".getClass();

        System.out.println(clazz == clazz2);
    }

    @Test
    public void info() {
        printClassInfo(Person.class);
        System.out.println("-------->info<----------");
        printClassInfo(String.class);
        System.out.println("-------->number<----------");
        printClassInfo(Number.class);
        System.out.println("-------->record<----------");
        printClassInfo(Point.class);
        System.out.println("-------->array<----------");
        printClassInfo(String[].class);
        System.out.println("-------->void<----------");
        // printClassInfo(void.class); void 也是一种数据类型
    }

    // JVM 动态加载字节码特性, 按需加载字节码, 当典型参考日志加载顺序, 加载了log4j, 就不会加载jdk的log
    static void printClassInfo(Class<?> clazz) {
        System.out.println("clazz.getName() = " + clazz.getName());
        System.out.println("clazz.getSimpleName() = " + clazz.getSimpleName());
        System.out.println("clazz.getSuperclass().getSimpleName() = " + clazz.getSuperclass().getSimpleName());
        System.out.println(clazz.isInterface());
        System.out.println(clazz.isPrimitive());
        System.out.println(clazz.isArray());
        System.out.println(clazz.isEnum());
        System.out.println(clazz.isRecord());
    }


    @Test
    public void testloadClass() {
        if (classIsPresent("log4j")) {
            // load log4j logging
        }else {
            // load jdk logging
        }
    }

    public static boolean classIsPresent(String name) {
        try {
            // 加载成功
            Class.forName(name);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    static record Point() {

    }
}
