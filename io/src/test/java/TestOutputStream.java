import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.GZIPInputStream;

/**
 * filter 模式, 装饰器模式
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestOutputStream {

    @Test
    public void testOutputStream_1() {
        try (InputStream input = new FileInputStream(new File("./img/1.jpeg"));
             OutputStream output = new FileOutputStream(new File("./img/3.jpeg"))) {
            input.transferTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOutputStream_2() throws IOException{
        // 内存中建立一个字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("hello".getBytes(StandardCharsets.UTF_8));

        String line = baos.toString(Charset.defaultCharset());

        System.out.println(line);
    }

    @Test
    @SneakyThrows
    public void testGzipInputStream() {
        // buffer, digest, cipher, gzip, zip 缓冲, 签名, 加密, 压缩  装饰流
        @Cleanup GZIPInputStream input = new GZIPInputStream(new FileInputStream("./img/1.jpeg"));
    }

    @Test
    @SneakyThrows
    public void cntInputStream() {
        // 计数功能的输入流, 33个字符
        @Cleanup CntInputStream input = new CntInputStream(new FileInputStream(Paths.get(".","img", "js", "1.js").toFile()));

        StringBuilder script = new StringBuilder();
        int len = 0;
        while ((len = input.read()) != -1) {
            char ch = (char)(len & 0xff);
            if (ch != ' ')
                script.append(ch);
        }

        System.out.println("script = " + script);
        int cnt = input.cnt();
        System.out.println("cnt = " + cnt);

        System.out.println("<-------------------------------->");

        // 读取jar包关键信息
        JarInputStream jis = new JarInputStream(new FileInputStream(Paths.get(".","img", "js", "1.js").toFile()),
                true);

        Manifest manifest = jis.getManifest();
        System.out.println(manifest);

    }


    /**
     * 流增强通常使用 FilterInputStream 类扩展
     */
    static class CntInputStream extends FilterInputStream {

        private AtomicInteger cnt;


        /**
         * Creates a {@code FilterInputStream}
         * by assigning the  argument {@code in}
         * to the field {@code this.in} so as
         * to remember it for later use.
         *
         * @param in the underlying input stream, or {@code null} if
         *           this instance is to be created without an underlying stream.
         */
        protected CntInputStream(InputStream in) {
            super(in);
            cnt = new AtomicInteger(0);
        }

        public int cnt() {
            return cnt.get();
        }

        @Override
        public int read() throws IOException {
            cnt.incrementAndGet();
            return super.read();
        }

        @Override
        public int read(byte[] b) throws IOException {

            int cnt = super.read(b);
            this.cnt.addAndGet(cnt);
            return  cnt;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int nLen = super.read(b, off, len);
            this.cnt.addAndGet(nLen);
            return nLen;
        }
    }

}
