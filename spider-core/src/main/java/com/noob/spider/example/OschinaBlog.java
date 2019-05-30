package com.noob.spider.example;

import com.noob.spider.pipeline.JsonFilePageModelPipeline;
import com.noob.spider.Site;
import com.noob.spider.model.OOSpider;
import com.noob.spider.model.annotation.ExtractBy;
import com.noob.spider.model.annotation.TargetUrl;

import java.util.Date;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.3.2
 */
@TargetUrl("http://my.oschina.net/flashsword/blog/\\d+")
public class OschinaBlog {

    @ExtractBy("//title/text()")
    private String title;

    @ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
    private String content;

    @ExtractBy(value = "//div[@class='BlogTags']/a/text()", multi = true)
    private List<String> tags;

    @ExtractBy("//div[@class='BlogStat']/regex('\\d+-\\d+-\\d+\\s+\\d+:\\d+')")
    private Date date;

    public static void main(String[] args) {
        //results will be saved to "/data/com.noob.spider.webmagic/" in json format
        OOSpider.create(Site.me(), new JsonFilePageModelPipeline("/data/com.noob.spider.webmagic/"), OschinaBlog.class)
                .addUrl("http://my.oschina.net/flashsword/blog").run();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }

}
