import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestInputStream {

    @Test
    public void testInputStream_1() {
        InputStream input = null;
        try {
            // 字节流处理, 文件是否存在与权限错误, 抛出异常
            input = new FileInputStream(Paths.get(".", "img", "js", "1.js").toFile());
            for (; ;) {
                int ch = input.read();
                System.out.print((char)ch);
                if (ch == -1)
                    break;

                // ThreadUtil.sleep(200);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testInputStream_2() throws IOException{
        try(InputStream in = new FileInputStream(Paths.get(".","img", "js", "1.js").toFile())) {
            int ch;
            TimeInterval timer = new TimeInterval();
            timer.start();
            while ((ch = in.read()) != -1) {
                System.out.print((char)ch);
            }

            System.out.println();
            System.out.println(timer.intervalRestart());
        }
//        finally {
//            input.close() twr 语法的处理, 识别接口, 自动在try块后面补充finally语句, 调用目标close()方法
//        }
    }

    // 一次读一个数据页
    private static final int BUFFER_SIZE = 8912;
    @Test
    public void testBuffer() {
        final Thread main = Thread.currentThread();
        final List<String> mainTheadStatus = new ArrayList<>();
        Thread t = new Thread(() -> {
            while (true) {
                String name = main.getState().name();
                mainTheadStatus.add(name);
            }
        });
        t.setDaemon(true);
        t.start();

        ThreadUtil.sleep(1000);

        File file = Paths.get(".","img", "1.jpeg").toFile();
        File out = Paths.get(".","img", "2.jpeg").toFile();
        try (InputStream input = new FileInputStream(file);
            OutputStream output = new FileOutputStream(out)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int readLen;
            int cnt = 0;
            while ((readLen = input.read(buffer)) != -1) {
                output.write(buffer,0, readLen);
                cnt++;
            }

            System.out.println("读取次数: " + cnt);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        mainTheadStatus
                .forEach(System.out::println);
    }

    @Test
    public void testInputStream_3() {
        try (InputStream input = new FileInputStream(new File("./img/js/1.js"))){
            String line = readAsString(input);
            System.out.println(line);

            byte[] msg = "hello world".getBytes(StandardCharsets.UTF_8);
            // 将内存中字节转换成输入流
            ByteArrayInputStream bais = new ByteArrayInputStream(msg);
            line = readAsString(bais);
            System.out.println(line);

        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 面向抽象编程
     * @param input 输入流
     * @return 输入流字符串
     * @throws IOException 读取异常
     */
    public static String readAsString(InputStream input) throws IOException {
        StringBuilder line = new StringBuilder();
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        for (;;) {
            len = input.read(buffer);
            if (len == -1)
                break;
            line.append(new String(buffer,0,len));
        }
        return line.toString();
    }

}
