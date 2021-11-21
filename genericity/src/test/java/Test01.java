import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Test01 {

    @Test
    public void test() {
        List list = new ArrayList();
        list.add("abc");
        String s = (String) list.get(0);
        System.out.println(s);

        list.add(123);

        String s1 = (String) list.get(1);
        System.out.println(s1);
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>(10);
        list.add("aa");

        // list.add(12); // 编译期间类型检查, 编译完成之后, 泛型会被擦除, 取值时的代码会根据泛型参数处理墙砖

        List<Integer> list2 = new ArrayList<>();
        // 关注泛型表达的边界
        List<? extends Number> list3 = list2;

        Pair<String, Object> pair = new Pair<>("a", "b",new Object().hashCode());
        System.out.println(pair);

        // Object o = new Integer(1).byteValue();

        Pair<String, Object> pair2 = new Pair<>("1","2",3);

//        if (pair2 instanceof Pair<Integer, String>) {
//           无法判断泛型类型
//        }


        Class<Pair> clazz = Pair.class;

        try {
            // 泛型参数类型的实例化
            Pair pair3 = clazz.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test3() {
        Class<SubPair> clazz = SubPair.class;
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType parameterizedType) {
            Type[] types = parameterizedType.getActualTypeArguments();
            // 泛型参数的类型
            Arrays.stream(types)
                    .forEach(t -> System.out.println(t.getTypeName()));
        }
    }

    static class SubPair extends Pair<String, Integer> {

        public SubPair(String first, String last, Integer key) {
            super(first, last, key);
        }
    }

    /**
     * 区分静态方法泛型类型 和 类实例的泛型类型
     * @param <T>
     * @param <K>
     */
    static class Pair<T, K> {
        private T first;
        private T last;
        private K key;

        public Pair(T first, T last, K key) {
            this.first = first;
            this.last = last;
            this.key = key;
        }

        public T getFirst() {
            return this.first;
        }

        public T getLast() {
            return this.last;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", last=" + last +
                    ", key=" + key +
                    '}';
        }

        public static <E, K> Pair<E,K> create(E first, E last, K key) {
            return new Pair<E, K>(first, last, key);
        }
    }
}
