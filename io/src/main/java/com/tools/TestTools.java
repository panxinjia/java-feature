package com.tools;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author xiaopantx
 * @version 1.0
 * @description Files Paths 连个处理文件常用的工具
 */
public class TestTools {
    private static final String DIR = System.getProperty("user.dir");

    @Test
    @SneakyThrows
    public void test() {
        // 读取文件, 创建, 删除, 判断, 获取信息, 操作小文件的便捷工具 Files
        // 表达文件系统路径的边界工具 paths
        // 读取文件的全部字节
        byte[] data = Files.readAllBytes(Paths.get(DIR, "img", "1.jpeg"));
        System.out.println(data.length);

        // 读取文本文件的所有行
        List<String> lines = Files.readAllLines(Paths.get(DIR, "pom.xml"));
        lines.forEach(System.out::println);

    }

}
