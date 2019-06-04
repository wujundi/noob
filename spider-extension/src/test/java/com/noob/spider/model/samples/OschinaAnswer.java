package com.noob.spider.model.samples;

import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.model.AfterExtractor;
import com.noob.spider.model.OOSpider;
import com.noob.spider.model.annotation.ExtractBy;
import com.noob.spider.model.annotation.HelpUrl;
import com.noob.spider.model.annotation.TargetUrl;

/**
 * @author code4crafter@gmail.com <br>
 */
@TargetUrl("http://www.oschina.net/question/\\d+_\\d+*")
@HelpUrl("http://www.oschina.net/question/*")
@ExtractBy(value = "//ul[@class='list']/li[@class='Answer']", multi = true)
public class OschinaAnswer implements AfterExtractor {

    @ExtractBy("//img/@title")
    private String user;

    @ExtractBy("//div[@class='detail']")
    private String content;

    public static void main(String[] args) {
        OOSpider.create(Site.me(), OschinaAnswer.class).addUrl("http://www.oschina.net/question/567527_120597").run();
    }

    @Override
    public void afterProcess(Page page) {

    }
}
