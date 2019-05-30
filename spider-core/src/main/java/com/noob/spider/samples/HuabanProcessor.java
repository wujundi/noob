package com.noob.spider.samples;

import com.noob.spider.pipeline.FilePipeline;
import com.noob.spider.processor.PageProcessor;
import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.Spider;
import com.noob.spider.downloader.selenium.SeleniumDownloader;

/**
 * 花瓣网抽取器。<br>
 * 使用Selenium做页面动态渲染。<br>
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 * Time: 下午4:08 <br>
 */
public class HuabanProcessor implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://huaban\\.com/.*").all());
        if (page.getUrl().toString().contains("pins")) {
            page.putField("img", page.getHtml().xpath("//div[@class='image-holder']/a/img/@src").toString());
        } else {
            page.getResultItems().setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        if (null == site) {
            site = Site.me().setDomain("huaban.com").setSleepTime(0);
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new HuabanProcessor()).thread(5)
                .addPipeline(new FilePipeline("/data/com.noob.spider.webmagic/test/"))
                .setDownloader(new SeleniumDownloader("/Users/yihua/Downloads/chromedriver"))
                .addUrl("http://huaban.com/")
                .runAsync();
    }
}
