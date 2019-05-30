package com.noob.spider.samples;

import com.noob.spider.processor.PageProcessor;
import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.Spider;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-4-21
 * Time: 下午8:08
 */
public class NjuBBSProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().regex("<a[^<>]*href=(bbstcon\\?board=Pictures&file=[^>]*)").all();
        page.addTargetRequests(requests);
        page.putField("title",page.getHtml().xpath("//div[@id='content']//h2/a"));
        page.putField("content",page.getHtml().smartContent());
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("bbs.nju.edu.cn");
    }

    public static void main(String[] args) {
        Spider.create(new NjuBBSProcessor()).addUrl("http://bbs.nju.edu.cn/board?board=Pictures").run();
    }
}
