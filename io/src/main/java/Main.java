import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    private static final String DIR = System.getProperty("user.dir");

    @Test
    @SneakyThrows
    public void testPath() {
        //Path 表达一个路径, 对文件夹件进行遍历操作使用path更加方便一些
        Path path = Paths.get(".", "src", "pom.xml");
        System.out.println(path);
        System.out.println(path.toAbsolutePath());
        System.out.println(path.normalize());
        // 表达规范路径
        System.out.println(path.toFile().getCanonicalPath());
        System.out.println("--------><----------");

        for(Path p: Paths.get(".")) {
            System.out.println(p.toFile().getCanonicalPath());
        }

        System.out.println("--------><----------");
        System.out.println(System.getProperty("user.dir"));

        System.out.println("--------><----------");
        recursionPrint(new File(".."), 0);
    }

    // 递归打印目录: 打印一个File的信息, 如果是目录递归打印
    static void recursionPrint(File dir, int depth) {
        if (!dir.exists())
            throw new RuntimeException("目录不存在~");

        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println(dir.getName());

        if (dir.isDirectory()) {
            File[] fs = dir.listFiles();
            int curDepth = depth + 1;
            assert fs != null;
            for (File f : fs) {
                recursionPrint(f, curDepth);
            }
        }

    }


    @Test
    public void testDir() {
        File file = new File(".");
//        file.mkdir(); 创建文件夹
//        file.mkdirs(); 递归创建文件夹
//        file.delete();  删除文件夹
    }

    @Test
    @SneakyThrows
    public void testListDir() {
        // 遍历和目录
        File userDir = new File(System.getProperty("user.dir"));
//        System.out.println(usrDir.getPath());
//        System.out.println(usrDir.getAbsolutePath());
//        System.out.println(usrDir.getCanonicalPath());
//        System.out.println(System.getProperty("java.class.path"));

        // 过滤文件
        File[] fs1 = userDir.listFiles();
        String s1 = Arrays.stream(fs1)
                .map(File::getName)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(s1);

        // 按照文件过滤
        File[] fs2
                = userDir.listFiles(pathname -> pathname.isDirectory() && pathname.getName().endsWith("src"));
        String s2 = Arrays.stream(fs2)
                .map(File::getName)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(s2);

        // 按照文件名过滤
        File[] fs3 = userDir.listFiles((dir, name) -> name.endsWith("xml"));
        String s3 = Arrays.stream(fs3).map(File::getName)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(s3);


    }

    @Test
    public void testCreateAndDelete() {
        // 存在, 创建, 删除
        File file = new File("1.txt");
        try {
            if (file.createNewFile()) {
                System.out.println(file.getCanonicalPath());
                Thread.sleep(5000);
                String fileName = file.getName();
                if (file.delete()) {
                    System.out.println(fileName + " 已经删除");
                }

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // 创建临时文件和删除
        try {
            // 临时文件用户程序运行过程中保存一些中间状态
            File tmpFile = File.createTempFile("tmp-", ".txt", new File("."));
            // 退出虚拟机时删除
            tmpFile.deleteOnExit();
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
            writer.write("创建临时文件, 写入");
            writer.flush();
            writer.close();

            Thread.sleep(5000);
            System.out.println("done");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFile() {
        // File 不存在时,不会主动报错, 创建File并没有触发任何磁盘操作, 使用File时注意判断是否存在
        File f1 = new File("src");
        File f2 = new File("pom.xml");

        System.out.println("f1.isFile() = " + f1.isFile()); // false
        System.out.println("f1.isDirectory() = " + f1.isDirectory()); // true
        System.out.println("f2.isFile() = " + f2.isFile()); // true
        System.out.println("f2.isDirectory() = " + f2.isDirectory()); // false

        // 读取文件权限
        System.out.println(f2.canRead());
        System.out.println(f2.canWrite());
        System.out.println(f2.canExecute());
        System.out.println(f2.length());
    }

    @SneakyThrows
    public static void main(String[] args) {

        // 默认从 user.dir 系统变量的相对位置寻找文件
        File file = new File("./README.md");
        // reader(file);
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        // 返回规范路径, 获取绝对路径时使用 getCanonicalPath
        System.out.println(file.getCanonicalPath());

        // 不同平台的默认常量
        System.out.println(File.pathSeparator); // :
        // 系统换行符, 一般是 \r\n表达换行
        System.out.println(System.getProperty("line.separator"));
        System.out.println(File.separator); // /
    }

    private static void reader(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 释放系统文件资源
        }
    }
}
