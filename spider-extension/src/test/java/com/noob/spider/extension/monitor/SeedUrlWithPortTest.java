package com.noob.spider.extension.monitor;

import com.noob.spider.core.Page;
import com.noob.spider.core.Site;
import com.noob.spider.core.Spider;
import com.noob.spider.core.processor.PageProcessor;
import com.noob.spider.extension.monitor.SpiderMonitor;
import org.junit.Test;

import javax.management.JMException;

/**
 * @author jerry_shenchao@163.com
 */
public class SeedUrlWithPortTest {

    @Test
    public void testSeedUrlWithPort() throws JMException {
        Spider spider = Spider.create(new TempProcessor()).addUrl("http://www.hndpf.org:8889/");
        SpiderMonitor.instance().register(spider);
        spider.run();
    }
}

class TempProcessor implements PageProcessor {

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return Site.me();
    }
}
