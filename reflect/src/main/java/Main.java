import com.bean.Person;
import org.junit.jupiter.api.Test;

public class Main {
    public static void main(String[] args) {
        Number n = new Double(12.1);
        // String s = new Double(22.21); compiler error





    }

    @Test
    public void classInstance() {

        // 业务类
        Class<Person> clazz = Person.class;
        // 内部类
        Class<Demo> demoClass = Demo.class;

        // 接口
        Class<Comparable> interfaceClass = Comparable.class;

        // 数组
        Class<String[]> arr = String[].class;
        Class<Integer> ints = Integer.class;

        // 无返回值表示
        Class<Void> voidClass = void.class;
        // 基础值
        Class<Integer> primitive = int.class;
    }

    static class Demo {

    }
}
