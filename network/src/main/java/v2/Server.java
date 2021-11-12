package v2;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Server {

    private static final AtomicInteger cnt = new AtomicInteger(0);

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup ServerSocket ss = new ServerSocket(8888);

        System.out.println("application server run....");
        while (true) {
            final Socket socket = ss.accept();
            final int count = cnt.incrementAndGet();
            String remoteAddress = socket.getInetAddress().getHostAddress();
            int remotePort = socket.getPort();
            System.out.println("第 [" + count + "]个用户访问服务器, " + "IP地址 [" + remoteAddress + "], " + "端口: [" + remotePort + "]");
            new Thread(() -> handleSocket(socket)).start();
        }
    }

    @SneakyThrows
    public static void handleSocket(Socket socket) {
        @Cleanup final InputStream input = socket.getInputStream();
        @Cleanup final OutputStream output = socket.getOutputStream();

//        DataInputStream dis = new DataInputStream(input);
//        DataOutputStream dos = new DataOutputStream(output);

        byte[] buff = new byte[4096];
        int len;
        while ((len = input.read(buff)) != -1) {
            final String address = socket.getInetAddress().getHostAddress();
            final int port = socket.getPort();
            System.out.println(address + ":" + port + " => " + new String(buff, 0, len));
            output.write(buff, 0, len);
            // 立即将消息发送回客户端
            output.flush();
        }


    }
}
