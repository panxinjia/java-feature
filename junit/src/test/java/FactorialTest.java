import cn.hutool.http.HttpUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @Test
    void testAssert() {
//        assertTrue(1 > 2, "1 不大于 2 正确");
//        assertFalse(1 > 2, "1 不大于2 错误");
        assertArrayEquals(new int[]{1, 2, 3, 4}, IntStream.rangeClosed(1, 4).toArray());
        assertNotNull(new Object());

//        浮点数运算错误值结果
//        assertEquals(0.1, Math.abs(1 - 9 / 10.0));
    }

    // 条件测试
    @Test
    @EnabledOnOs({OS.WINDOWS})
    void testWindows() {
        System.out.println("test windows");
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testLinux() {
        System.out.println("test linux");
    }

    @Test
    void testAssert2() {
//        特定参数的场景会抛出异常, 测试是否会被捕获
        assertThrows(IllegalArgumentException.class, () -> {
           Factorial.factorial(-1);
        });

//        assertThrows(ArithmeticException.class, () -> {
//           Factorial.factorial();
//        });
    }

    @Test
    @Disabled
    void testDisable() {

    }
h
    @SneakyThrows
    @Test
    void url() {
        String line = HttpUtil.get("https://www.baidu.com");
        System.out.println(line);
    }

    @Test
    @Disabled
    void testDisable2() {

    }

    @BeforeEach
    void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tear Down");
    }

    @Test
    void factorial() {
        // assert
        assertEquals(1, Factorial.factorial(1));
        assertEquals(2, Factorial.factorial(2));
        assertEquals(6, Factorial.factorial(3));
    }
}