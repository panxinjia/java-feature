import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestDbUtils {

    private static ComboPooledDataSource ds = null;
    private static QueryRunner qr = null;

    @BeforeEach
    public void init() {
        try {
            ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8");
            ds.setUser("root");
            ds.setPassword("123456");

            qr = new QueryRunner(ds);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    @Test
    @SneakyThrows
    public void testJdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate(ds, false);
        // System.out.println(template);
        String sql = "select * from students";
        List<Map<String, Object>> list = template.queryForList(sql);
        list.forEach(System.out::println);

        System.out.println("<-------------------------------->");
        List<String> sqls = IntStream.rangeClosed(1, 100)
                .mapToObj(val -> "insert into users(username, password) values('" + val + "', '" + val + "')")
                .collect(Collectors.toList());
        int[] rows = template.batchUpdate(sqls.toArray(new String[0]));

        long cnt = Arrays.stream(rows).asLongStream()
                .reduce(0, Long::sum);
        System.out.println("cnt = " + cnt);
    }

    @Test
    @SneakyThrows
    public void testQueryRunner2() {
        String sql = "select * from students";
        List<Object[]> list = qr.query(sql, new ArrayListHandler());
        // 标记结果
        list.forEach(objects -> System.out.println(Arrays.toString(objects)));
    }

    @Test
    public void testQueryRunner() {
        QueryRunner qr = new QueryRunner(ds);
        try {
            String sql = "select cust_id, cust_name, email, birth from customer where cust_id = ?";
            Map<String, Object> result = qr.query(sql,
                    resultSet -> {
                        Map<String, Object> map = new HashMap<>();
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int cnt = rsmd.getColumnCount();
                        while (resultSet.next()) {
                            for (int i = 1; i <= cnt; i++) {
                                String key = rsmd.getColumnName(i);
                                Object value = resultSet.getObject(key);
                                map.put(key, value);
                            }
                        }
                        return map;
                    }, 1);

            System.out.println("result = " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // todo
        }
    }
}
