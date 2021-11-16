import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.junit.jupiter.api.Test;

import javax.print.event.PrintServiceAttributeListener;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {


    public static void args(int a, Object... obs) {
        System.out.println(a);

        System.out.println("-------->可变参数<----------");
        // [Ljava.lang.Object; 可变参数, 结果数组
        System.out.println(obs.getClass().getName());
        for (int i = 0; i < obs.length; i++) {
            System.out.println(obs[i]);
        }
    }


    public static void main(String[] args) {
        args(1, 23, new Object(), new String[10]);
        System.out.println("--------><----------");

        // args 应用参数
        Arrays.stream(args)
                .forEach(System.out::println);
        System.out.println("-------->param<----------");
        // 字面量, 符号常量
        int a;
        a = 20;
        int b = 1, c = 2;

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        // byte short int long float double char reference class interface array
        System.out.println();

        System.out.println(10);
        System.out.println(0x0A);
        System.out.println(0b1010);
        System.out.println(012);
        System.out.println(0.314e+1);

        System.out.println("hello\bworld");

        System.out.println((int) 'a');
        System.out.println((int) '1');

        System.out.println('2' + 2);
        System.out.println((int) '2' + 2);

        System.out.println("中".getBytes());
        System.out.println(Arrays.toString("中".getBytes(StandardCharsets.UTF_8)));
        int i1 = -28 << 16;
        int i2 = -72 << 8;
        int i3 = -83;
        // 最高位0 表示一个字符, 最高位是1表示一个字节表示不了一个字符
        System.out.println(Integer.toBinaryString(-28));
        System.out.println(Integer.toBinaryString(-72));
        System.out.println(Integer.toBinaryString(-83));
        System.out.println(Integer.toBinaryString(i1));
        System.out.println(Integer.toBinaryString(i2));
        System.out.println(Integer.toBinaryString(i3));
        System.out.println(i1 + i2 + i3);
        System.out.println("-------->char<----------");

        System.out.println(Integer.toBinaryString('a'));
        double PI = Math.PI;
//        文本处理扫描器
        Scanner scanner = new Scanner(System.in);
//        System.out.println("输入半径");
//        final int R = scanner.nextInt();
//        System.out.println("周长:" + 2 * PI * R);
//        System.out.println("面积:" + PI * Math.pow(R, 2));

        scanner.useDelimiter("-").tokens()
                .forEach(System.out::println);
        scanner.close();
    }

    @Test
    public void test() {
        // 取余获取个位上的数,  / 去掉各位数
        int num = 54321;
        int pos;
        while (num != 0) {
            pos = num % 10;
            num /= 10;
            System.out.print(pos);
        }
    }

    @Test
    public void test2() {
        System.out.println(10 + 2 + "A");
        System.out.println(Integer.toBinaryString(-6));
    }

    @Test
    public void test3() {
        int m = 32;
        int n = 100;
        final int[] array = generatorRandomNumber(m, n);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.stream(array).allMatch(val -> val >= 32 && val <= 100));

    }


    @Test
    public void test4() {
        // 公5  母3 小1  共100文
        for (int x = 1; x <= 19; x++) {
            for (int y = 1; y <= 31; y++) {
                int z = 100 - x - y;
                if ((5 * x + 3 * y + z / 3 == 100) && (z % 3 == 0)) {
                    System.out.println(x + " \t" + y + "\t" + z);
                }
            }
        }
    }

    @Test
    public void test5() {
        int[] arr = {3, 1, 2};
        // 变量复制 和 内存申请
        Integer[] arr2 = new Integer[]{1, 2, 3, 4, 7, 4};
        int[] arr3 = new int[10];

        // 位置检索
        final int i = find(arr2, 7);
        System.out.println(i);
    }

    // 数组检索
    public static <E> int find(E[] arr, E target) {
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 生成随机数, 数组结果
     *
     * @param m 左边界
     * @param n 右边界
     * @return 数组结果
     */
    public static int[] generatorRandomNumber(int m, int n) {
        return IntStream.generate(() -> (int) (Math.random() * (n - m)) + m).limit(10000)
                .toArray();
    }

    @Test
    public void testArrays1() {
        final int[] arr = generatorRandomNumber(10, 10000);
//        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        // 二分查找 logN 复杂度
        final int[] copy = Arrays.copyOf(arr, arr.length);
        System.out.println(Arrays.toString(copy));

        // 复制10个元素
        final int[] copyOfRange = Arrays.copyOfRange(arr, 0, 10);
        System.out.println(Arrays.toString(copyOfRange));

        System.out.println(Arrays.equals(arr, copy));
    }

    @Test
    public void testException() {
        try {
            int i = 10;
            int ret = i / 0;
        } catch (Exception e) {
            final StackTraceElement[] stackTrace = e.getStackTrace();
            final String msg = e.getMessage();
            System.out.println("msg = " + msg);

            Arrays.stream(stackTrace)
                    // 调用栈详细信息 msg
                    .forEach(stackTraceElement -> {
                        final int lineNumber = stackTraceElement.getLineNumber();
                        final String fileName = stackTraceElement.getFileName();
                        final String className = stackTraceElement.getClassName();
                        final String methodName = stackTraceElement.getMethodName();

                        System.out.println(lineNumber + " " + fileName + " " + className + " " + methodName);
                    });
        }

    }

    @Test
    public void testBox() {
        Integer ch = new Integer(20013);
        final int reverse = Integer.reverse(ch);
        System.out.println(reverse);

        // 无符号整数
        final long l = Integer.toUnsignedLong(ch);
        System.out.println(l);

        System.out.println(Integer.MIN_VALUE - 1 == Integer.MAX_VALUE);
        // 字节
        System.out.println(Integer.BYTES);
        // 位数
        System.out.println(Integer.SIZE);

        System.out.println(Byte.BYTES + " " + Byte.SIZE);

    }

    @Test
    public void testRuntimeUtil() {
        RuntimeUtil.execForLines("ifconfig")
                .stream()
                .filter(line -> line.contains("192.168"))
                .forEach(System.out::println);

        long totalMemory = RuntimeUtil.getTotalMemory();
        System.out.println(totalMemory / 1024 / 1024);
    }

    @Test
    public void testDesensitizedUtil() {
        // 数据脱敏工具
        String phone = DesensitizedUtil.mobilePhone("1361071075");
        System.out.println(phone);

        String cardNum = DesensitizedUtil.idCardNum("130324199409293011", 3, 4);
        System.out.println(cardNum);

        String email = DesensitizedUtil.email("m15132582590@163.com");
        System.out.println(email);

        String pwd = DesensitizedUtil.password("root");
        System.out.println(pwd);

        String chineseName = DesensitizedUtil.chineseName("张三");
        System.out.println(chineseName);

        Long userId = DesensitizedUtil.userId();
        System.out.println(userId);

        String address = DesensitizedUtil.address("北京市房山区,稻田镇", 5);
        System.out.println(address);

        // 脱敏工具
        String desensitized = DesensitizedUtil.desensitized("13610710475", DesensitizedUtil.DesensitizedType.MOBILE_PHONE);
        System.out.println(desensitized);

        final TimeInterval interval = new TimeInterval();
    }

    @Test
    public void testTimeInterval() {
//        TimeInterval interval = new TimeInterval();
//
//        interval.start("1");
//        ThreadUtil.sleep(1000);
//
//        interval.start("2");
//        ThreadUtil.sleep(2000);
//
//        System.out.println("group-1 -> " + interval.interval("1"));
//        System.out.println("group-2 -> " + interval.interval("2"));

        final TimeInterval timer = new TimeInterval();

// 分组1
        timer.start("1");
        ThreadUtil.sleep(800);

// 分组2
        timer.start("2");
        ThreadUtil.sleep(900);

        Console.log("Timer 1 took {} ms", timer.intervalMs("1"));
        Console.log("Timer 2 took {} ms", timer.intervalMs("2"));

    }
}
