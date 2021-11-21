import cn.hutool.core.date.TimeInterval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestList {

    private static final Random RANDOM = new Random();

    public static Integer findMissingNumber(List<? extends Integer> list, int lo, int hi) {
        Integer target = null;
        for(int i = lo; i <= hi; i++) {
            if (!list.contains(i)) {
                target = i;
                break;
            }
        }
        return target;
    }

    @Test
    public void test4() {
        String s1 = "a";
        String s2 = new String("a");

        System.out.println(Objects.equals(s1,s2));
    }

    @Test
    public void test3() {
        // equals: 引用类型的等值逻辑判断
        // hashCode: 对象信息的摘要, 一般来说都是唯一的
        int lo = 1;
        int hi = 20000;
        List<Integer> list = new ArrayList<>();
        for (int i = lo; i <= hi; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Integer source = list.remove(RANDOM.nextInt(hi));
        Integer target = findMissingNumber(list,lo, hi);
        System.out.println("source = " + source + ", target = " + target);

        Assertions.assertEquals(source, target);
    }

    @Test
    public void testNull() {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        System.out.println(list);

        // 集合转换数组
        list.toArray(Integer[]::new);

        List<Integer> subList = list.subList(0, 1);
        System.out.println(subList);

        // 只读列表不支持存储null值
        // List<Integer> list2 = List.of(123, 3, null);
    }

    @Test
    @Disabled
    public void testList() {
        // 随机访问性能高, 添加删除性能低
        //List<Integer> ints = new ArrayList<>(100_0000);

        // 随机访问性能很低, 添加删除性能高
        List<Integer> ints = new LinkedList<Integer>();
        IntStream.rangeClosed(1, 100_0000).forEach(ints::add);

        TimeInterval timer = new TimeInterval();
        timer.start();
        for (int i = 0; i < 20_0000_00; i++) {
            ints.get(RANDOM.nextInt(100_0000));
        }

        long ms = timer.intervalRestart();
        System.out.println("随机访问2000_0000_00万次消耗时间: " + ms + " 毫秒");


    }
}
