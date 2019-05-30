package com.noob.spider.model.samples;

import com.noob.spider.Page;
import com.noob.spider.Site;
import com.noob.spider.model.AfterExtractor;
import com.noob.spider.model.OOSpider;
import com.noob.spider.model.annotation.ExtractBy;
import com.noob.spider.model.annotation.TargetUrl;

import java.util.List;

/**
 * @author yihua.huang@dianping.com <br>
 *         Date: 13-8-13 <br>
 *         Time: 上午10:13 <br>
 */
@TargetUrl("http://*.alpha.dp/*")
public class DianpingFtlDataScanner implements AfterExtractor {

	@ExtractBy(value = "(DP\\.data\\(\\{.*\\}\\));", type = ExtractBy.Type.Regex, notNull = true, multi = true)
	private List<String> data;

	public static void main(String[] args) {
		OOSpider.create(Site.me().setSleepTime(0), DianpingFtlDataScanner.class)
				.thread(5).run();
	}

	@Override
	public void afterProcess(Page page) {
		if (data.size() > 1) {
			System.err.println(page.getUrl());
		}
		if (data.size() > 0 && data.get(0).length() > 100) {
			System.err.println(page.getUrl());
		}
	}
}
