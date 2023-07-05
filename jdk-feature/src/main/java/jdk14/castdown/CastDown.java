package jdk14.castdown;

/**
 * 向下转型语法: 代码简洁性的语法糖
 * @author xiaopantx
 */
public class CastDown {

    public static void main(String[] args) {
        Person p = new Student();

        // 经典写法
        if (p instanceof  Student) {
            Student stu = (Student) p;
            System.out.println(stu);
        }

        // jdk14提供的语法糖 - 简化转换
        if(p instanceof  Student stu) {
            System.out.println(stu);
        }
    }
}

class Person {}

class Student extends Person{}
