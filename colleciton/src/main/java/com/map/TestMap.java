package com.map;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.beans.Introspector;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author xiaopantx
 * @version 1.0
 * @description 哈希表特点: 空间换时间, 扩容机制为2^n
 */
public class TestMap {

    @Test
    public void test() {
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("tom", 20);
            put("jerry", 18);
        }};
        Integer value = map.put("tom", 100);
        System.out.println(value); // put 返回旧值
        System.out.println(map.get("tom")); // 获取新值

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }

        // all keys
        Set<String> keys = map.keySet();

        // all values
        Collection<Integer> values = map.values();

    }

    @Test
    public void testMapCache() {
        // 使用map作为缓存
        Students ss = Students.builder()
                .cache(new HashMap<>())
                .values(List.of(
                        new Student("tom", 100 ),
                        new Student("jerry", 60 ),
                        new Student("bob", 200 ),
                        new Student("adda", 90 )
                )).build();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100_0000; i++) {
            ss.getScore("tom");
            ss.getScore("jerry");
            ss.getScore("bob");
            ss.getScore("adda");
            ss.getScore("abc");
//            ss.findInList("tom");
//            ss.findInList("jerry");
//            ss.findInList("bob");
//            ss.findInList("adda");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("运行时间: " + (endTime - startTime) + " 毫秒");

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student {
        private String name;
        private Integer score;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Students {
        private List<Student> values;
        // 缓存学生姓名和分数
        private Map<String, Integer> cache;

        public Integer getScore(String name) {
            // 缓存中保存了对应值, 直接获取
            Integer cacheScore = cache.get("name");
            if (Objects.nonNull(cacheScore))
                return cacheScore;
            // 缓存中没有值, 先查找, 再放入缓存
            Integer ret = this.findInList(name);
            if (Objects.nonNull(ret)) {
                cache.put("name", ret);
                return ret;
            }
            return null;
        }

        public Integer findInList(String name) {
            return values.stream()
                    .filter(stu -> stu.getName().equals(name))
                    .map(Student::getScore)
                    .findFirst()
                    .orElse(null);

        }
    }

    @Test
    public void testEnumMap() {
        //EnumMap 节奏紧凑, 节约空间且查询效率高
        Map<DayOfWeek, String> map = new EnumMap<DayOfWeek, String>(DayOfWeek.class);
        // 表达星期
        map.put(DayOfWeek.SUNDAY, "星期日");
        map.put(DayOfWeek.MONDAY, "星期一");
        map.put(DayOfWeek.TUESDAY, "星期二");
        map.put(DayOfWeek.WEDNESDAY, "星期三");
        System.out.println(map);
    }

    @SneakyThrows
    @Test
    public void prop() {
        Properties prop = new Properties();
        InputStream input = this.getClass().getResourceAsStream("/db.properties");
        prop.load(input);

        prop.list(System.out);
        // 迭代
        Set<Map.Entry<Object, Object>> entries = prop.entrySet();
        entries.stream().forEach(System.out::println);

        // URL构建
        URL url = URI.create("http://www.baidu.com")
                .toURL();

        String protocol = url.getProtocol();
        String host = url.getHost();
        System.out.println(protocol + "://" + host);

//        表达配置文件
//        OutputStream os = new FileOutputStream(Paths.get(System.getProperty("user.dir"), "db.xml").toFile());
//        prop.storeToXML(os, "storeXMl", Charset.defaultCharset());
    }


}
