package v2;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup Socket socket = new Socket("192.168.1.12",8888);
        @Cleanup final OutputStream output = socket.getOutputStream();
        @Cleanup final InputStream input = socket.getInputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("控制台输入信息: " + line);
            writer.write(line);
            writer.flush();

            final byte[] echo = input.readAllBytes();
            System.out.println("服务端返回 => " + new String(echo, Charset.defaultCharset()));
        }



    }

}
