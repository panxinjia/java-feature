import lombok.*;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static final String username = "root";
    private static final String password = "123456";

    @SneakyThrows
    @Test
    public void testConnection() {
        String url = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
        String username = "root";
        String pwd = "123456";

        Connection connection = DriverManager.getConnection(url, username, pwd);
        System.out.println(connection);
    }

    @Test
    @SneakyThrows
    public void testTransaction() {
        Connection connection = DriverManager.getConnection(url,username, password);
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        try {
            // 关闭事务自动提交
            connection.setAutoCommit(false);

            //DML 数据操作语言

            // 提交事务
            connection.commit();
        }catch(Exception e) {
            // 回滚时
            connection.rollback();
        }finally {
            connection.close();
        }

    }

    @Test
    @SneakyThrows
    public void testBatch() {
        String sql = "insert into students(name, gender, grade, score) values (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i <= 10; i++) {
            statement.setObject(1, "tom" + i);
            statement.setObject(2,i);
            statement.setObject(3, i * 100);
            statement.setObject(4,i * 200);
            // 批量操作
            statement.addBatch();
        }

        int[] rows = statement.executeBatch();
        Arrays.stream(rows)
                .forEach(System.out::println);

        statement.close();
        connection.close();

    }

    @Test
    @SneakyThrows
    public void testInsert() {
        // 新增, 返回主键, 对于自增主键的数据库, 执行时处理
        String sql = "insert into students(name, gender, grade, score) values (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, "tom");
        statement.setObject(2, 1);
        statement.setObject(3, 1);
        statement.setObject(4, 100);
        int rows = statement.executeUpdate();
        System.out.println("新增数据: [" + rows + "]条");
        ResultSet rs = statement.getGeneratedKeys();
        while (rs.next()) {
            int primaryKey = rs.getInt(1);
            System.out.println("primaryKey = " + primaryKey);
        }

        rs.close();
        statement.close();
        connection.close();

    }

    @Test
    @SneakyThrows
    public void testUpdate() {
        // 更新数据
        String sql = "update students set score = ? where id = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, 90);
        statement.setObject(2, 13);
        int rows = statement.executeUpdate();
        System.out.println("更新数据: [" + rows + "]条");

        statement.close();
        connection.close();

    }

    @Test
    @SneakyThrows
    public void testDelete() {
        // 删除数据
        String sql = "delete from students where id = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, 13);

        int rows = statement.executeUpdate();
        System.out.println("删除数据: [" + rows + "]条");

        statement.close();
        connection.close();
    }

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select id, name, gender, grade, score from students where id = ?";
            conn = DriverManager.getConnection(url, username, password);
            //statement = conn.createStatement();
            statement = conn.prepareStatement(sql);
            statement.setObject(1, 1);
            //resultSet = statement.executeQuery(sql);
            resultSet = statement.executeQuery();

            List<Student> list = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                Integer gender = resultSet.getInt(3);
                Integer grade = resultSet.getInt(4);
                Integer score = resultSet.getInt("score");
                // bean 属性值封装
                Student stu = Student.builder().id(id).name(name).gender(gender).grade(grade).score(score).build();
                list.add(stu);
            }

            list.stream()
                    .forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student {
        private Integer id;
        private String name;
        private Integer gender;
        private Integer grade;
        private Integer score;

    }
}
