import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseDao<T> {

    private Class<T> entityType;

    public BaseDao(){
        // 具体子类实例化时,会执行读取泛型参数的代码
        Class<? extends BaseDao> subClazz = this.getClass();
        // 读取通用父类
        Type type = subClazz.getGenericSuperclass();
        // 如果是带泛型的类型
        if (type instanceof ParameterizedType) {
            // 转换成泛型类型
            ParameterizedType parameterizedType = (ParameterizedType) type;
            // 读取泛型参数数组
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types.length == 0) {
                throw new RuntimeException("泛型参数读取失败~");
            }
            entityType = (Class<T>) types[0];
        }
    }

    public int insert(T t) {
        try {
            // 通过class来创建实例
            t = entityType.newInstance();
            // 子类需要的类型
            String subClassName = t.getClass().getSimpleName();
            System.out.println(subClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        PersonDao personDao = new PersonDao();
        userDao.insert(new User()); // User
        personDao.insert(new Person()); // Person
    }
}

class UserDao extends BaseDao<User> {}
class PersonDao extends BaseDao<Person> {}
class User {}
class Person {}
