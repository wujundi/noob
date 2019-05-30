package com.noob.spider.model.samples;

import com.noob.spider.model.ConsolePageModelPipeline;
import com.noob.spider.model.annotation.ExtractBy;
import com.noob.spider.model.annotation.TargetUrl;
import com.noob.spider.Site;
import com.noob.spider.model.OOSpider;

/**
 * @author code4crafter@gmail.com
 */
@TargetUrl("http://meishi.qq.com/beijing/c/all[\\-p2]*")
@ExtractBy(value = "//ul[@id=\"promos_list2\"]/li",multi = true)
public class QQMeishi {

    @ExtractBy("//div[@class=info]/a[@class=title]/h4/text()")
    private String shopName;

    @ExtractBy("//div[@class=info]/a[@class=title]/text()")
    private String promo;

    public static void main(String[] args) {
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), QQMeishi.class).addUrl("http://meishi.qq.com/beijing/c/all").thread(4).run();
    }

}
