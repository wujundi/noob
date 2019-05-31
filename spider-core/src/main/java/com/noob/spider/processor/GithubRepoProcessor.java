package com.noob.spider.processor;

import com.noob.spider.Page;
import com.noob.spider.ResultItems;
import com.noob.spider.Site;
import com.noob.spider.Task;
import com.noob.spider.downloader.MockGithubDownloader;
import junit.framework.Assert;
import org.junit.Test;
import com.noob.spider.model.OOSpider;
import com.noob.spider.pipeline.Pipeline;


/**
 * @author code4crafter@gmail.com
 */
public class GithubRepoProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        page.putField("star",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[2]//a[@class='social-count js-social-count']/text()").toString());
        page.putField("fork",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[3]//a[@class='social-count']/text()").toString());
    }

    @Override
    public com.noob.spider.Site getSite() {
        return Site.me();
    }

    @Test
    public void test() {
        OOSpider.create(new GithubRepoProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                Assert.assertEquals("78",((String)resultItems.get("star")).trim());
                Assert.assertEquals("65",((String)resultItems.get("fork")).trim());
            }
        }).setDownloader(new MockGithubDownloader()).test("https://github.com/code4craft/webmagic");
    }

}
