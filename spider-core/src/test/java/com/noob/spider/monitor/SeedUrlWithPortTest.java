package com.noob.spider.monitor;

import org.junit.Test;
import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.Spider;
import com.noob.spider.processor.PageProcessor;

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
