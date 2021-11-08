import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
}
