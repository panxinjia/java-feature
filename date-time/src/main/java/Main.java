import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {


    @Test
    public void testFormat() {
        int n = 12340;

        System.out.println(n);
        System.out.println(Integer.toHexString(n));

        // 数值格式化
        System.out.println(NumberFormat.getCurrencyInstance().format(n));

        System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(n));
    }

    @Test
    @SneakyThrows
    public void testDate() {
        Date date = new Date();

        System.out.println(date.getYear() + 1900);
        System.out.println(date.getMonth() + 1);
        System.out.println(date.getDay());

        // 时区标准时间
        System.out.println(date.toGMTString());
        // 本地时间
        System.out.println(date.toLocaleString());

        // 格式化参数
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        Date now = dateFormat.parse(format);
        System.out.println(format);
        System.out.println(now.toLocaleString());

    }

    @Test
    @SneakyThrows
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.WEEK_OF_MONTH)); // 2
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH)); // 8
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK)); // 2 星期一

        System.out.println(calendar.get(Calendar.HOUR_OF_DAY)); // 一天的第几个小时 23
        System.out.println(calendar.get(Calendar.HOUR)); // 小时 11
        System.out.println(calendar.get(Calendar.MINUTE)); // 分钟 18
        System.out.println(calendar.get(Calendar.SECOND)); // 秒 11秒

        // 转换date
        Date date = calendar.getTime();

        // calendar.clear(); 清除时间
    }

    @Test
    public void testLocal() {
        // 构造新的api表达
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.now();

        System.out.println(ld);
        System.out.println(lt);
        System.out.println(ldt);

        LocalDate ld2 = LocalDate.of(2021, 10, 20);
        LocalTime lt2 = LocalTime.of(20,20,21,111);
        LocalDateTime ldt2 = LocalDateTime.of(ld2, lt2);
        System.out.println(ldt2);

        System.out.println(ldt2.getYear());
        System.out.println(ldt2.getMonth().getValue());
        System.out.println(ldt2.getDayOfMonth());
        System.out.println(ldt2.getHour());
        System.out.println(ldt2.getMinute());
        System.out.println(ldt2.getSecond());
        System.out.println(ldt2.getNano());

        // with 不可变类设计, 调整之后返回新的对象元素
        // set  可变类设计, 调整之后返回新类
        LocalDateTime now = LocalDateTime.now();
        // 调整月份
        final LocalDateTime l1 = now.withMonth(1);
        // 读取月份值
        System.out.println(l1.getMonthValue());

        // 增加
        now.plus(1, ChronoUnit.MONTHS);

        // 时间格式化
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final String ss = formatter.format(now);
        System.out.println(ss);

    }

    @Test
    public void testRandom() {
        final Random random = new Random();
        // 100个 10-1000的随机数
        random.ints(100, 10, 1000)
                .boxed()
                .forEach(System.out::println);

        System.out.println("--------><----------");

        // 随机数生成
        final boolean allMatch = random.doubles(100, 10, 20)
                .boxed()
                .allMatch(val -> val >= 10 && val <= 20);

        System.out.println("allMatch = " + allMatch);

    }

    @Test
    public void testString() {
        String s = new String(new char[] {'a', 'b', 'c'});

        System.out.println(s.length());
        System.out.println(s.isEmpty());
        System.out.println(s.isBlank());
        System.out.println(s.strip());


        StringBuilder sb = new StringBuilder();
        StringJoiner joiner = new StringJoiner("-","[","]");
        joiner.setEmptyValue("");
        joiner.add("abc")
                .add("e")
                .add(" ")
                .add("")
                .add(null); // null值拼接处理
        System.out.println(joiner.toString());

    }
}
