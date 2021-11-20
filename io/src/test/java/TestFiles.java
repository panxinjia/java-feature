import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestFiles {

    private static final Path PATH = Paths.get("./img", "js", "1.js");
    @Test
    @SneakyThrows
    public void testFiles() {
        Path path = Paths.get("./img", "js", "1.js");
        byte[] bs = Files.readAllBytes(path);

        String script = new String(bs, Charset.defaultCharset());
        System.out.println(script);
        System.out.println("<-------------------------------->");
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("<-------------------------------->");
        String line = Files.readString(path, Charset.defaultCharset());
        System.out.println(line);
    }

    @Test
    @SneakyThrows
    public void testMethods() {
        // Files.deleteIfExists()
//        Path result = Files.copy(PATH, Paths.get("./img","js", "2.js"), StandardCopyOption.REPLACE_EXISTING);
//        System.out.println(result.normalize());

        Path path = Files.walkFileTree(Paths.get("."), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //System.out.println(dir.toFile().getName());
                // System.out.println("pre");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // System.out.println("visitFile");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                //System.out.println("visitFileFailed");
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // System.out.println("post");
                return FileVisitResult.CONTINUE;
            }
        });

        // 访问器模式设计,
        path = Files.walkFileTree(Paths.get("."),
                Set.of(FileVisitOption.FOLLOW_LINKS),
                20,
                new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        // 是否为一个符号连接
                        boolean symbolicLink = attrs.isSymbolicLink();

                        return null;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        return null;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        return null;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        return null;
                    }
                });

        System.out.println(path.normalize());
    }
}
