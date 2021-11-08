package com.conn.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static final String username = "root";
    private static final String password = "123456";

    @Test
    @SneakyThrows
    public void testHikaricp() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        // 连接超时时间10s
        config.setConnectionTimeout(1000_0L);
        // 连接池最少持有20个连接
        config.setMinimumIdle(20);
        // 连接池最大持有200个连接
        config.setMaximumPoolSize(200);
        // 不活跃连接60秒后断开
        config.setIdleTimeout(60_000L);
        System.out.println(config);

        DataSource dataSource = new HikariDataSource(config);

        // 创建, 或直接获取一个不活跃连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        // 并不是将底层tcp连断开, 只是将连接标记为空闲状态, 放回连接池
        connection.close();

    }
}
