package com.noob.spider.extension.downloader.selenium;

import org.junit.Ignore;
import org.junit.Test;
import selenium.SeleniumDownloader;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 *        Time: 下午2:46 <br>
 */
public class SeleniumDownloaderTest {

	private String chromeDriverPath = "/Users/yihua/Downloads/chromedriver";

	@Ignore("need chrome driver")
	@Test
	public void test() {
		selenium.SeleniumDownloader seleniumDownloader = new selenium.SeleniumDownloader(chromeDriverPath);
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			Page page = seleniumDownloader.download(new Request("http://huaban.com/"), new Task() {
				@Override
				public String getUUID() {
					return "huaban.com";
				}

				@Override
				public Site getSite() {
					return Site.me();
				}
			});
			System.out.println(page.getHtml().$("#waterfall").links().regex(".*pins.*").all());
		}
		System.out.println(System.currentTimeMillis() - time1);
	}

    @Ignore
	@Test
	public void testBaiduWenku() {
		selenium.SeleniumDownloader seleniumDownloader = new SeleniumDownloader(chromeDriverPath);
        seleniumDownloader.setSleepTime(10000);
		long time1 = System.currentTimeMillis();
		Page page = seleniumDownloader.download(new Request("http://wenku.baidu.com/view/462933ff04a1b0717fd5ddc2.html"), new Task() {
			@Override
			public String getUUID() {
				return "huaban.com";
			}

			@Override
			public Site getSite() {
				return Site.me();
			}
		});
		System.out.println(page.getHtml().$("div.inner").replace("<[^<>]+>","").replace("&nsbp;","").all());
	}

}
