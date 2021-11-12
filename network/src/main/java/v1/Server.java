package v1;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiaopantx
 * @version 1.0
 * @description 网络通讯
 */
public class Server {

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup ServerSocket ss = new ServerSocket(9999);
        @Cleanup Socket socket = ss.accept();
        InputStream input = socket.getInputStream();
        @Cleanup DataInputStream dis = new DataInputStream(input);
        int port = socket.getPort();
        String address = socket.getInetAddress().getHostAddress();
        String msg = dis.readUTF();
        msg = String.format("%s:%d => %s", address, port, msg);
        System.out.println(msg);
    }
}
