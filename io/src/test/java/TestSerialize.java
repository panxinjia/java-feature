import cn.hutool.system.oshi.OshiUtil;
import lombok.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestSerialize {

    @Test
    @SneakyThrows
    public void testSerialize() {


        User user = User.builder()
                .id(1).name("tom").age(20)
                .build();
        @Cleanup ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Paths.get("./img", "user.obj").toFile()));
        oos.writeObject(user);

        oos.writeDouble(Double.MAX_VALUE);
        oos.writeBoolean(Boolean.TRUE);

        // 读取之前刷新缓冲
        oos.flush();

        @Cleanup ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Paths.get("./img","user.obj").toFile()));

        Object o = ois.readObject();
        double d = ois.readDouble();
        boolean b = ois.readBoolean();
        System.out.println(o);
        System.out.println(d);
        System.out.println(b);


    }

    @Data
    @Builder
    static class User
        implements Serializable
    {
        // 序列化版本号, ClassNotFoundException  InvalidException 两种可能异常
        static final long serialVersionUID = 42L;

        private Integer id;
        private String name;
        private Integer age;

        public User() {
            // 反序列化直接由jvm构造对象, 不会调用构造器
            System.out.println("empty constructor");
        }

        public User(Integer id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

    }
}
