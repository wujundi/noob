package com.noob.spider.samples;

import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.Spider;
import com.noob.spider.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 */
public class IteyeBlogProcessor implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex(".*yanghaoli\\.iteye\\.com/blog/\\d+").all());
        page.putField("title",page.getHtml().xpath("//title").toString());
        page.putField("content",page.getHtml().smartContent().toString());
    }

    @Override
    public Site getSite() {
        if (site == null) {
            site = Site.me().setDomain("yanghaoli.iteye.com");
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new IteyeBlogProcessor()).thread(5).addUrl("http://yanghaoli.iteye.com/").run();
    }
}
