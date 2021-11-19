import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
@Slf4j
public class DemoConnection {
    private static final String url = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static Properties info;


    @BeforeEach
    public void init() {
        info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
    }

    @Test
    public void createConnection() {
        final AtomicInteger ai = new AtomicInteger();
         IntStream.rangeClosed(1, 20000)
                 .forEach(val -> new Thread(() -> {
                     try {
                         // 测试最大连接数量
                         Connection connection = DriverManager.getConnection(url, info);
                         if (connection != null) {
                             int cnt = ai.incrementAndGet();
                             System.out.println("申请连接数量: " + cnt);
                         }
                     } catch (SQLException e) {
                         e.printStackTrace();
                         System.out.println("ai = " + ai.get());
                     }
                     ThreadUtil.sleep(10_0000);
                 }).start());


        LockSupport.park();
    }

    @Test
    public void testTransactionIsolationLevel() {
//        @Cleanup var conn = getConnection();
//         半个字节的位表示事务隔离级别
//        System.out.println(Connection.TRANSACTION_REPEATABLE_READ);

    }

    @Test
    public void testTransaction() {
        var conn = getConnection();
        try {
            conn.setAutoCommit(false);
            String sql = "update account set balance = balance - ? where id = ?";
            int row = executeDML(sql, conn, 100, 1);
            log.info(row != 0 ? "转账 -> 扣减成功~" : "转账 -> 扣减失败");
            // int i = 1;
            // i = i / 0; // 发生错误
            sql = "update account set balance = balance + ? where id = ?";
            row = executeDML(sql, conn, 100, 2);
            log.info(row != 0 ? "转账 -> 增加成功~" : "转账 -> 增加失败");

            // 执行成功, 提交事务
            conn.commit();
        } catch (Exception e) {
            String msg = e.getMessage();
            log.info("msg => {}", msg);
            try {
                // 执行失败, 数据回滚
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                log.info("数据回滚");
            }
        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int executeDML(String sql, Connection connection, Object... args) throws SQLException {
        int row = 0;
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        row = ps.executeUpdate();
        return row;
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, info);
    }

    @Test
    @SneakyThrows
    public void testBlob() {
        String sql = "insert into customer values(null, ?, ?, ?, ?)";
        @Cleanup Connection conn = DriverManager.getConnection(url, info);
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "张三");
        ps.setString(2, "abc@qq.com");
        ps.setDate(3, new Date(System.currentTimeMillis()));
        String path = "/Users/xiaopantx/IdeaProjects/java-feature/jdbc/img";
        ps.setBlob(4, img(path));
        int rows = ps.executeUpdate();
        log.info("rows => {}", rows);
    }

    @Test
    @SneakyThrows
    public void testBlobRead() {
        String sql = "select * from customer where cust_id = ?";
        @Cleanup Connection conn = DriverManager.getConnection(url, info);
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, 1);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Blob blob = rs.getBlob("photo");
            // 读取blob, 处理流结构
            @Cleanup InputStream input = blob.getBinaryStream();
            @Cleanup FileOutputStream output = new FileOutputStream("/Users/xiaopantx/IdeaProjects/java-feature/jdbc/img" + "/3.jpeg");
            byte[] buff = new byte[1024];
            int len;
            while ((len = input.read(buff)) != -1) {
                output.write(buff, 0, len);
                output.flush();
            }
        }

    }

    @SneakyThrows
    public static InputStream img(String path) {
        return new FileInputStream(new File(path + "/1.jpeg"));
    }


    @Test
    @SneakyThrows
    public void testMetaData() {
        // 连接元数据
        @Cleanup Connection connection = DriverManager.getConnection(url, info);
        DatabaseMetaData metaData = connection.getMetaData();
        // 数据库模式名, 对于mysql来说,是获取数据库名称
        @Cleanup ResultSet schemas = metaData.getSchemas();

        // 模式分类
        ResultSet catalogs = metaData.getCatalogs();
        // 临时表, 系统表, 系统视图, 数据表, 数据视图
        ResultSet tableTypes = metaData.getTableTypes();
        // printResultSet(tableTypes);

        // 读取所有表名
        ResultSet tables = metaData.getTables("learnjdbc", null, null, null);
        printResultSet(tables);
    }

    @SneakyThrows
    public static void printResultSet(ResultSet rs) {
        ResultSetMetaData rsmd = rs.getMetaData();
        int cnt = rsmd.getColumnCount();
        final StringBuilder labels = new StringBuilder();
        IntStream.rangeClosed(1, cnt)
                .forEach(val -> {
                    try {
                        String labelName = rsmd.getColumnLabel(val);
                        labels.append(String.format("%16s\t", labelName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(labels);
        while (rs.next()) {
            StringBuilder record = new StringBuilder();
            for (int i = 1; i <= cnt; i++) {
                record.append(String.format("%16s\t", rs.getObject(i)));
            }
            System.out.println(record);
        }
    }

    @Test
    @SneakyThrows
    public void testPerformance() {
        // 权限检查, 语法解析, 语法校验, 执行, 返回结果
        Connection conn = DriverManager.getConnection(url, info);
        Statement st = conn.createStatement();
        AtomicInteger ai = new AtomicInteger(0);
        TimeInterval timer = new TimeInterval();
        timer.start();
        for (int i = 0; i < 2000; i++) {
            String sql = "insert into users(username, password) values('tom" + i + "', 'jerry')";
            int row = st.executeUpdate(sql);
            ai.addAndGet(row);
        }
        long interval = timer.intervalRestart();
        System.out.println("statement 插入2000 条数据消耗时间 = " + interval + " 毫秒");


        String sql = "insert into users(username, password) values(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        TimeInterval timer2 = new TimeInterval();
        timer2.start();
        for (int i = 0; i < 200; i++) {
            ps.setString(1, "jerry" + i);
            ps.setString(2, "jerry");
            int row = ps.executeUpdate();
            ai.addAndGet(row);
        }
        long interval2 = timer2.intervalRestart();
        System.out.println("preparedStatement 插入2000 条数据消耗时间 = " + interval2 + " 毫秒");

        close(null, st, conn);

        System.out.println("共插入记录: " + ai.get());


    }

    @SneakyThrows
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            rs.close();
        }

        if (st != null) {
            st.close();
        }

        if (conn != null) {
            conn.close();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User {
        private String username;
        private String password;
    }

    @Test
    @SneakyThrows
    public void testSQLInjection() {
        String username = "tom";
        String password = "tom";
        // 用户输入sql片段, 绕过sql语法
        password = "tom' or '1' = '1";

        String sql = "select username, password from users where username = '" +
                username + "' and password = '" + password + "'";
        List<User> users = get(sql, User.class);
        System.out.println(users);

        assert users.size() != 0 : "登录成功";
    }

    @SneakyThrows
    public static <T> List<T> get(String sql, Class<T> clazz) {

        @Cleanup Connection conn = DriverManager.getConnection(url, info);
        @Cleanup Statement st = conn.createStatement();
        @Cleanup ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<T> users = new ArrayList<>();
        while (rs.next()) {
            T instance = clazz.getConstructor().newInstance();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = rsmd.getColumnName(i);
                Object value = rs.getObject(columnLabel);
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(instance, value);
            }
            users.add(instance);
        }

        return users;
    }

    @Test
    @SneakyThrows
    public void executeDML() {
        String sql = "insert into users(username, password) values('tom', 'tom'),('jerry', 'jerry')";
        @Cleanup Connection connection = DriverManager.getConnection(url, info);
        @Cleanup Statement st = connection.createStatement();
        int rows = st.executeUpdate(sql);
        System.out.println(rows);
    }

    @Test
    @SneakyThrows
    public void executeDDL() {
        String sql = "create table users (" +
                "username varchar(20) not null, " +
                "password varchar(20) not null" +
                ")";

        @Cleanup Connection connect = DriverManager.getConnection(url, info);
        @Cleanup Statement statement = connect.createStatement();
        int result = statement.executeUpdate(sql);
        assert result == 0 : "执行DDL成功";
    }

    @Test
    @SneakyThrows
    public void connect_4() {
        // 配置文件保存配置信息, 面向抽象的编程中不出现第三方的api, 使用接口定义访问规范
        InputStream input = DemoConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties prop = new Properties();
        prop.load(input);

        @Cleanup Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop);
        System.out.println(connection);
    }


    @Test
    @SneakyThrows
    public void connect_3() {

        //tring className = "com.mysql.jdbc.Driver";
        //Class<?> clazz = Class.forName(className);
        //Constructor constructor = clazz.getConstructor();
        //Driver driver = (Driver) constructor.newInstance();
        // DriverManager.registerDriver(driver);


        @Cleanup Connection connect = DriverManager.getConnection(url, info);
        System.out.println(connect);


    }

    @Test
    @SneakyThrows
    public void connect_2() {
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Constructor<?> constructor = clazz.getConstructor();
        Driver driver = (Driver) constructor.newInstance();
        @Cleanup Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }


    @SneakyThrows
    @Test
    public void connect_1() {
        // 获取jdbc连接
        Driver driver = null;
        driver = new com.mysql.jdbc.Driver();

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        @Cleanup Connection conn = driver.connect("jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8",
                info);
        System.out.println(conn);
    }
}
