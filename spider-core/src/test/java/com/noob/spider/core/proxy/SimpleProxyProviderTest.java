package com.noob.spider.core.proxy;

import com.noob.spider.core.Site;
import com.noob.spider.core.Task;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/4/16
 *         Time: 上午10:29
 */
public class SimpleProxyProviderTest {

    public static final Task TASK = Site.me().toTask();

    @Test
    public void test_get_proxy() throws Exception {
        com.noob.spider.core.proxy.Proxy originProxy1 = new com.noob.spider.core.proxy.Proxy("127.0.0.1", 1087);
        com.noob.spider.core.proxy.Proxy originProxy2 = new com.noob.spider.core.proxy.Proxy("127.0.0.1", 1088);
        com.noob.spider.core.proxy.SimpleProxyProvider proxyProvider = SimpleProxyProvider.from(originProxy1, originProxy2);
        Proxy proxy = proxyProvider.getProxy(TASK);
        assertThat(proxy).isEqualTo(originProxy1);
        proxy = proxyProvider.getProxy(TASK);
        assertThat(proxy).isEqualTo(originProxy2);
        proxy = proxyProvider.getProxy(TASK);
        assertThat(proxy).isEqualTo(originProxy1);
    }
}
