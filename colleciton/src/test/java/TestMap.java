import com.google.common.cache.CacheBuilder;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestMap {

    @Test
    void testMap() {
        Map<String, String> map = new HashMap<>();
        map = new TreeMap<>((o1, o2) -> o2.length() - o1.length());
        map.put("lisi","28");
        map.put("zhangsan","29");

        Set<Map.Entry<String, String>> entrys = map.entrySet();
        entrys.forEach(System.out::println);
    }
}
