import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    @Test
    @SneakyThrows
    public void testInetAddress() {
        // InetAddress 表达一个网络地址
        InetAddress ia = InetAddress.getByName("192.168.1.12");
        // 主机地址
        String hostAddress = ia.getHostAddress();
        // 主机名
        String hostName = ia.getHostName();
        // 标准化主机名
        String canonicalHostName = ia.getCanonicalHostName();

        System.out.println("hostAddress = " + hostAddress);
        System.out.println("hostName = " + hostName);
        System.out.println("canonicalHostName = " + canonicalHostName);
    }

    @Test
    @SneakyThrows
    public void testInetSocketAddress() {
        // 未解析的网络应用地址, 处理主机名
        // InetSocketAddress isa = InetSocketAddress.createUnresolved("192.168.1.12",9999);
        //InetSocketAddress isa = new InetSocketAddress(9999);
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName("192.168.1.12"), 9999);
        InetAddress address = isa.getAddress();
        int port = isa.getPort();

        // 主机名
        String hostName = isa.getHostName();

        System.out.println("hostName = " + hostName);
        // 完全地址 2
        System.out.println(address + ":" + port);
    }
}
