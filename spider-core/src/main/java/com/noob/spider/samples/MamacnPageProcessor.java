package com.noob.spider.samples;

import com.noob.spider.processor.PageProcessor;
import com.noob.spider.samples.pipeline.OneFilePipeline;
import com.noob.spider.scheduler.FileCacheQueueScheduler;
import com.noob.spider.selector.Selectable;
import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.Spider;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author code4crafer@gmail.com
 */
public class MamacnPageProcessor implements PageProcessor {

    private Site site = Site.me().setDomain("www.mama.cn").setSleepTime(100);

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//ul[@id=ma-thumb-list]/li").nodes();
        StringBuilder accum = new StringBuilder();
        for (Selectable node : nodes) {
            accum.append("img:").append(node.xpath("//a/@href").get()).append("\n");
            accum.append("title:").append(node.xpath("//img/@alt").get()).append("\n");
        }
        page.putField("",accum.toString());
        if (accum.length() == 0) {
            page.setSkip(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("http://www\\.mama\\.cn/photo/.*\\.html").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Spider.create(new MamacnPageProcessor())
                .setScheduler(new FileCacheQueueScheduler("/data/com.noob.spider.webmagic/mamacn"))
                .addUrl("http://www.mama.cn/photo/t1-p1.html")
                .addPipeline(new OneFilePipeline("/data/com.noob.spider.webmagic/mamacn/data"))
                .thread(5)
                .run();
    }
}
