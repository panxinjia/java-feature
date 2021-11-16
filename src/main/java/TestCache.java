import cn.hutool.cache.CacheListener;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Soundbank;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestCache {

    @Test
    public void fifo() {
        final FIFOCache<String, String> cache = new FIFOCache<>(20, 10L);

        // 1秒打印一次缓存内容
        ThreadUtil.execute(() -> {
            while (true) {
                ThreadUtil.sleep(1000);
                // 缓存淘汰监听
                cache.setListener((key, cachedObject) -> System.out.println("淘汰缓存: " + key));
            }
        });

        // 每秒放入10个缓存元素
        ThreadUtil.execute(() -> {
            IntStream.rangeClosed(1,10_0000)
                    .forEach(val -> {
                        String value = String.valueOf(val);
                        cache.put(value, value);
                        ThreadUtil.sleep(100);
                    });
        });

        LockSupport.park();
    }
}
