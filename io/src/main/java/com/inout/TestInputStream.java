package com.inout;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author xiaopantx
 * @version 1.0
 * @description 字节输入输出流
 *
 * 缓冲, 加密, 计算摘要, zip包
 */
public class TestInputStream {



    @SneakyThrows
    public static void main(String[] args) {
//        File source = new File("./io/img/1.jpeg");
//        File target = new File("./io/img/2.jpeg");
//        copyFile(source, target);

//        byte[] data;
        // 指向内存的输出流, toByteArray 转成数组
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        output.write("hello".getBytes(StandardCharsets.UTF_8));
//        output.write("world".getBytes(StandardCharsets.UTF_8));
//
//        data = output.toByteArray();
//
//        System.out.println(new String(data));

//        System.out.println("-------->操作zip包<----------");
//        FileInputStream input = new FileInputStream(new File("/Users/xiaopantx/IdeaProjects/java-feature/io/target/io-1.0-SNAPSHOT.jar"));
//        ZipInputStream in = new ZipInputStream(input);
//        ZipEntry entry;
//        while ((entry = in.getNextEntry()) != null) {
//            String entryName = entry.getName();
//            System.out.println(entryName);
//            if (!entry.isDirectory()) {
//                int n;
//                while ((n = in.read()) != -1) {
//                    System.out.print((char)n);
//                }
//                System.out.println();
//            }
//        }
        
        System.out.println("-------->读取类路径资源<----------");
        // 读取系统资源
        InputStream input = ClassLoader.getSystemResourceAsStream("db.properties");
        System.out.println(input);

        TestInputStream tis = new TestInputStream();
        // 读取类路径资源
        InputStream input2 = tis.getClass().getResourceAsStream("/db.properties");
        System.out.println(input2);

        InputStream input3 = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
        System.out.println(input3);
    }

    /**
     * 文件拷贝
     * @param source 源文件
     * @param target 目标文件
     */
    static void copyFile(File source, File target) {
        if (!source.exists() || !source.isFile())
            throw new RuntimeException("文件不存在");
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(target)) {
            byte[] buff = new byte[4096];
            int n;
            while ((n = input.read(buff)) != -1) {
                output.write(buff, 0, n);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void testByteArrayInputStream() {
        String s = "hello \n world";
        System.out.println(s);
        byte[] bs = s.getBytes(StandardCharsets.UTF_8);
        String ret = readAsString(new ByteArrayInputStream(bs));
        System.out.println(ret);
    }

    // 读取输入流中的数据转换成字符串, 面向对象多态的运用, 面向抽象编程
    @SneakyThrows
    static String readAsString(InputStream input) {
        StringBuilder sb = new StringBuilder();
        // 带行号
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(input));
        //BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(reader.getLineNumber()).append(":").append(line);
            // 读取一行, 处理换行符
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    @Test
    @SneakyThrows
    public void testInputStreamCache() {
        try (InputStream input = new FileInputStream("pom.xml")) {
            // 缓冲区最好是4096的整数倍, 磁盘一次操作读取一个页
            byte[] buff = new byte[4096];
            int n;
            while ((n = input.read(buff)) != -1) {
                String str = new String(buff, 0, n, StandardCharsets.UTF_8);
                System.out.println(str);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInputStream() {
        // 字节顶层抽象类
        InputStream input = null;
        try {
            input = new FileInputStream(new File("pom.xml"));
            for (;;) {
                // 每次读取一个字节
                int ch = input.read();
                if (ch == -1) {
                    break;
                }
                System.out.print((char)ch);
            }
            System.out.println();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long startTime = System.nanoTime();
    private long runTime = 0L;
    private long endTime = System.nanoTime();
    @BeforeEach
    public void before() {
        startTime = System.nanoTime();
    }

    @AfterEach
    public void after() {
        endTime = System.nanoTime();
        runTime = endTime - startTime;
        System.out.println("运行时间:[" + runTime/1000_000.0 + "]微秒");
    }
}
