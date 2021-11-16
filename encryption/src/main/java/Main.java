import cn.hutool.core.net.url.UrlBuilder;
import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    /**
     * 编码算法
     *  编码算法不是加密算法, 编码算法都是通用的
     */
    @Test
    public void urlEncoderTest() {
        // 中文和特殊字符转换成%XX表示, 网络传输兼容性问题, 传输ASCII编码
        String url = "https://www.baidu.com?wd=sort&gen=123&name=汤姆";
        String encode = URLEncoder.encode(url, Charset.defaultCharset());
        System.out.println("encode = " + encode);

        String decode = URLDecoder.decode(encode, Charset.defaultCharset());
        System.out.println("decode = " + decode);

        int ch = 0xE5A786;
        System.out.println((char)ch);
    }

    @Test
    public void testBase64() {
        // 将二进制数据编码成文本,  在处理文本的程序中可以处理二进制数据
        System.out.println(0b111111);
        byte[] bys = "hello".getBytes(StandardCharsets.UTF_8);
        String encode = Base64.getEncoder().encodeToString(bys);
        System.out.println(encode);

        byte[] decode = Base64.getDecoder().decode(encode);
        System.out.println(new String(decode, 0, decode.length, StandardCharsets.UTF_8));


        // URL中使用Base64编码
        String url = "https://www.baidu.com?wd=sort&gen=123&name=汤姆";
        String urlEnocde = Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
        System.out.println(urlEnocde);

        byte[] decode1 = Base64.getUrlDecoder().decode(urlEnocde);
        System.out.println(new String(decode1, 0, decode1.length, Charset.defaultCharset()));
    }

    /**
     * 哈希算法, 摘要算法
     */
    @Test
    public void testDigest() {

    }
}
