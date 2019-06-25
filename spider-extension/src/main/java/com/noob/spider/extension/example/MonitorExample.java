package com.noob.spider.extension.example;

import com.noob.spider.core.Spider;
import com.noob.spider.extension.monitor.SpiderMonitor;
import com.noob.spider.core.processor.example.GithubRepoPageProcessor;
import com.noob.spider.core.processor.example.ZhihuPageProcessor;

/**
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public class MonitorExample {

    public static void main(String[] args) throws Exception {

        Spider zhihuSpider = Spider.create(new ZhihuPageProcessor())
                .addUrl("http://my.oschina.net/flashsword/blog");
        Spider githubSpider = Spider.create(new GithubRepoPageProcessor())
                .addUrl("https://github.com/code4craft");

        SpiderMonitor.instance().register(zhihuSpider);
        SpiderMonitor.instance().register(githubSpider);
        zhihuSpider.start();
        githubSpider.start();
    }
}
