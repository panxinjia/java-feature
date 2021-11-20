import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestFile {

    @Test
    public void testFile() {
        String lineSeparator = System.getProperty("line.separator");
        String pathSeparator = File.pathSeparator;
        String fileSeparator = File.separator;

        System.out.println("lineSeparator = " + lineSeparator);
        System.out.println("pathSeparator = " + pathSeparator);
        System.out.println("fileSeparator = " + fileSeparator);

        System.out.println("<-------------------------------->");

        String userDir = System.getProperty("user.dir");
        System.out.println("userDir = " + userDir);
    }

    @Test
    public void testFilePath() {
        File file = new File(".", "img/1.jpeg");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        try {
            // 返回规范化绝对路径
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关于File特点, 真正发生io操作时, 如果文件不存在才会报错
        File f = new File("abc");
        if (f.exists() && f.isFile()) {
            // 文件存在, 并且是个文件
        }

        if(f.exists() && f.isDirectory()) {
            // 文件存在, 并且是个目录
        }
    }

    @Test
    public void testFileAttribute() {
        // 读取文件属性
        File file = new File(".", "img/1.jpeg");

        boolean canRead = file.canRead();
        boolean canWrite = file.canWrite();
        boolean canExecute = file.canExecute();

        long bys = file.length();
        System.out.println("canRead = " + canRead);
        System.out.println("canWrite = " + canWrite);
        System.out.println("canExecute = " + canExecute);
        System.out.println(bys / 1024 / 1024.0);
    }

    @Test
    public void testFile2() {
        // 文件创建, 删除, 操作
        File file = new File("./img/1.txt");
        try {
            if (!file.exists() && file.createNewFile()) {
                System.out.println(file.getName() + " 创建成功~");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                // 删除成功
                System.out.println("文件删除成功~");
            }
        }

        // 临时文件
        try {
            File tmpFile = File.createTempFile("test", ".md");
            tmpFile.deleteOnExit(); //  退出时删除临时文件
            System.out.println(tmpFile.exists());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListFiles() {
        File file = new File(".");
        File[] fs = file.listFiles();
        Arrays.stream(fs)
                        .forEach(System.out::println);

        System.out.println("<-------------------------------->");

        File[] fs2 = file.listFiles(path -> path.getPath().contains("."));
        Arrays.stream(fs2).forEach(System.out::println);

        System.out.println("<-------------------------------->");

        File[] fs3 = file.listFiles((dir, name) -> !name.endsWith(".xml"));
        Arrays.stream(fs3).forEach(System.out::println);
        // mkdir mkdirs delete 创建文件夹, 删除文件夹
    }


}
