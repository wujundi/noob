package com.noob.spider.samples;

import com.noob.spider.Site;
import com.noob.spider.Page;
import com.noob.spider.Spider;
import com.noob.spider.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class HuxiuProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex(".*article.*").all();
        page.addTargetRequests(requests);
        page.putField("title",page.getHtml().xpath("//div[@class='clearfix neirong']//h1/text()"));
        page.putField("content",page.getHtml().xpath("//div[@id='neirong_box']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("www.huxiu.com");
    }

    public static void main(String[] args) {
        Spider.create(new HuxiuProcessor()).addUrl("http://www.huxiu.com/").run();
    }

}
