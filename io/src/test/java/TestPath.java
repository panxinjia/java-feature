import jdk.jfr.Unsigned;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestPath {

    @Test
    public void testPath() throws IOException {
        // Path 表达一个文件系统的路径
        System.out.println(123);

        // 表达多级路径
        Path path = Paths.get("./img", "js", "html", "component");

        System.out.println(path.toAbsolutePath());
        System.out.println(path.normalize());
        System.out.println(path.toFile().getAbsolutePath());
        System.out.println(path.toFile().getCanonicalPath());

        System.out.println("<-------------------------------->");


        Path parent = Paths.get("..").toAbsolutePath();
        for(Path p : parent) {
            Path name = p.getFileName();
            System.out.println(name);
        }

        int cnt = parent.getNameCount();
        System.out.println("文件个数: " + cnt);

    }

    @Test
    public void testPrintFile() {
        printFiles(new File(System.getProperty("user.dir")), 0);
    }

    /**
     * 递归打印目录
     * @param pathName 目录
     * @param level 目录层级, 默认从0开始
     */
    public static void printFiles(File pathName, int level) {
        if (Objects.isNull(pathName)) {
            pathName = new File(".");
        }

        for (int i = 0; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(pathName.getName() + (pathName.isDirectory() ? "/" : ""));

        if (pathName.isDirectory()) {
            int lv = ++level;
            File[] fs = pathName.listFiles();
            if (fs != null && fs.length != 0) {
                Arrays.stream(fs)
                        .forEach( f -> {
                            printFiles(f,lv);
                        });
            }
        }
    }
}
