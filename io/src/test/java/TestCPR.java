import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestCPR {

    @Test
    public void test() {
        // 1. 静态资源放在classpath中读取, 避免资源对于操作系统路径的依赖
        // 2. clazz 对象加载资源使用绝对路径, 类加载器使用相对路径
        InputStream input = this.getClass().getResourceAsStream("/db.properties");
        input = this.getClass().getClassLoader().getResourceAsStream("/db.properties");
        if (Objects.nonNull(input)) {
            Properties properties = new Properties();
            try {
                properties.load(input);

                properties.list(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
