package com.noob.manage.gather.commons;

import com.noob.spider.core.Page;
import com.noob.spider.core.Request;
import com.noob.spider.core.Site;
import com.noob.spider.core.Task;
import com.noob.spider.core.downloader.AbstractDownloader;
import com.noob.spider.core.selector.PlainText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CasperjsDownloader
 *
 * @author Gao Shen
 * @version 16/7/1
 */
@Component
public class CasperjsDownloader extends AbstractDownloader {
    private final static Logger LOG = LogManager.getLogger(CasperjsDownloader.class);
    @Autowired
    private Casperjs casperjs;

    @Override
    public Page download(Request request, Task task) {
        String html = null;
        Site site = null;
        if (task != null) {
            site = task.getSite();
        }
        try {
            html = casperjs.gatherHtml(new com.noob.manage.model.commons.Request(request.getUrl(), true));
        } catch (Exception e) {
            if (site.getCycleRetryTimes() > 0) {
                // 2019-06-04 找不到相应方法，故暂时注释掉
//                return addToCycleRetry(request, site);
            }
            request.putExtra("EXCEPTION", e);
            onError(request);
            return null;
        }
        Page page = new Page();
        page.setRawText(html);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        onSuccess(request);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
    }
}

// 2019-07-29 22：49 Casperjs是一个基于PhantomJS和SlimerJS的前端端对端测试框架，在这里应该是为了实现所谓“js页面抓取”