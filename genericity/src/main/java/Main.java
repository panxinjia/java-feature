import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 泛型作为一种代码模板
        ArrayList<Integer> list = new ArrayList<>();
        // 面向对象向上转型时, 类本身的继承关系可以, 但泛型类型与继承无关, 编译器报错
        //ArrayList<Object> nums = list; // compile error
        //ArrayList<Number> 和 ArrayList<Integer> 没有任何继承关系

        Collection<Integer> collection = list; // success 成功!


    }

    @Test
    public void test1() {

        // 没有声明泛型, 编译器当做Object处理
        List list = new ArrayList();
        list.add("tom");
        list.add("jerry");
        String s = (String) list.get(0);
        String s1 = (String) list.get(1);
        System.out.println("s = " + s);
        System.out.println("s1 = " + s1);

    }
    @Test
    public void test2() {
        // 泛型参数 转型无关
        List<Number> number = new ArrayList<>();
        number.add(Float.MAX_VALUE);
        number.add(Integer.MIN_VALUE);
        number.add(Double.MIN_VALUE);

        System.out.println(number);

    }

    //泛型接口
    @Test
    public void testGenericInter() {
        List<Person> ps = new ArrayList<>();
        ps.add(new Person("tom", 18));
        ps.add(new Person("jerry", 20));
        System.out.println(ps);

        // Arrays.sort(ps.toArray());  java.lang.ClassCastException 泛型接口, 要求排序元素有Comparable接口定义的访问点
        // < 0  == 0 > 0
        Arrays.sort(ps.toArray(ps.toArray(new Person[0])), Comparator.comparingInt(Person::getAge));
        System.out.println(ps);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Person {
        private String name;
        private Integer age;
    }

}
