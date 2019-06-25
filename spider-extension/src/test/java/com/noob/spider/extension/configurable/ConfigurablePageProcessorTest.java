package com.noob.spider.extension.configurable;

import com.noob.spider.core.ResultItems;
import com.noob.spider.core.Site;
import com.noob.spider.core.Spider;
import com.noob.spider.extension.downloader.MockGithubDownloader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 * @date 14-4-5
 */
public class ConfigurablePageProcessorTest {

    @Test
    public void test() throws Exception {
        List<ExtractRule> extractRules = new ArrayList<ExtractRule>();
        ExtractRule extractRule = new ExtractRule();
        extractRule.setExpressionType(ExpressionType.XPath);
        extractRule.setExpressionValue("//title");
        extractRule.setFieldName("title");
        extractRules.add(extractRule);
        extractRule = new ExtractRule();
        extractRule.setExpressionType(ExpressionType.XPath);
        extractRule.setExpressionValue("//ul[@class='pagehead-actions']/li[1]//a[@class='social-count js-social-count']/text()");
        extractRule.setFieldName("star");
        extractRules.add(extractRule);
        ResultItems resultItems = Spider.create(new ConfigurablePageProcessor(Site.me(), extractRules))
                .setDownloader(new MockGithubDownloader()).get("https://github.com/code4craft/webmagic");
        assertThat(resultItems.getAll()).containsEntry("title", "<title>code4craft/webmagic · GitHub</title>");
        assertThat(resultItems.getAll()).containsEntry("star", " 86 ");

    }
}
