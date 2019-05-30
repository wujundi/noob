package com.noob.spider.model.samples;

import com.noob.spider.model.OOSpider;
import com.noob.spider.model.annotation.ExtractBy;
import com.noob.spider.model.annotation.TargetUrl;
import com.noob.spider.Site;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-8-2 <br>
 * Time: 上午7:52 <br>
 */
@TargetUrl("http://*.iteye.com/blog/*")
public class IteyeBlog implements Blog{

    @ExtractBy("//title")
    private String title;

    @ExtractBy(value = "div#blog_content",type = ExtractBy.Type.Css)
    private String content;

    @Override
    public String toString() {
        return "IteyeBlog{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me(), IteyeBlog.class).addUrl("http://flashsword20.iteye.com/blog").run();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
