package v1;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author xiaopantx
 * @version 1.0
 * @description 实现两个jvm进程之间的网络通讯
 */
public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup Socket socket = new Socket("localhost",9999);

        val os = socket.getOutputStream();
        @Cleanup val dos = new DataOutputStream(os);
        dos.writeUTF("你好~");


    }
}
