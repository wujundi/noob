package com.noob.spider.samples;

import com.noob.spider.downloader.PhantomJSDownloader;
import com.noob.spider.pipeline.CollectorPipeline;
import com.noob.spider.pipeline.ResultItemsCollectorPipeline;
import com.noob.spider.processor.PageProcessor;
import com.noob.spider.Page;
import com.noob.spider.ResultItems;
import com.noob.spider.Site;
import com.noob.spider.Spider;

import java.util.List;

/**
 * Created by dolphineor on 2014-11-21.
 * <p>
 * 以淘宝为例, 搜索冬装的相关结果
 */
public class PhantomJSPageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setDomain("s.taobao.com")
            .setCharset("GBK")
            .addHeader("Referer", "http://www.taobao.com/")
            .setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        if (page.getRawText() != null)
            page.putField("html", page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        PhantomJSDownloader phantomDownloader = new PhantomJSDownloader().setRetryNum(3);

        CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();

        Spider.create(new PhantomJSPageProcessor())
                .addUrl("http://s.taobao.com/search?q=%B6%AC%D7%B0&sort=sale-desc") //%B6%AC%D7%B0为冬装的GBK编码
                .setDownloader(phantomDownloader)
                .addPipeline(collectorPipeline)
                .thread((Runtime.getRuntime().availableProcessors() - 1) << 1)
                .run();

        List<ResultItems> resultItemsList = collectorPipeline.getCollected();
        System.out.println(resultItemsList.get(0).get("html").toString());
    }

}
