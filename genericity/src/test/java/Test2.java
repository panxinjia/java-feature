import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Test2 {

    @Test
    void name() {

        String val = PairHelper.add(new Pair<Number>(1, 2));
        System.out.println(val);

        // 不指定泛型边界, 报错
        String val2 = PairHelper.add(new Pair<Integer>(10, 20));

        String val3 = PairHelper.add(new Pair<>(Double.MIN_VALUE, Double.MAX_VALUE));

        String val4 = PairHelper.add(new Pair<>(new BigDecimal(10), new BigDecimal(100)));

        System.out.println(val2);
        System.out.println(val3);
        System.out.println(val4);

        new Pair2<Integer>();
        new Pair2<Double>();

        // new Pair2<Object>(); 泛型已经定义了边界
    }


    static class PairHelper {
        public static String add(Pair<? extends Number> pair) {
            Number first = pair.getFirst();
            Number last = pair.getLast();
            // pair.setFirst(new Integer(20) + 100);
            return pair.getFirst() + "-" + pair.getLast();
        }
    }

    static class Pair2<T extends Number> {

    }

    static class Pair<T> {
        private T first;
        private T last;

        public Pair(T first, T last) {
            this.first = first;
            this.last = last;
        }

        public T getFirst() {
            return first;
        }

        public T getLast() {
            return last;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setLast(T last) {
            this.last = last;
        }

        public static <E> Pair<E> create(E first, E last) {
            return new Pair<>(first, last);
        }
    }
}
