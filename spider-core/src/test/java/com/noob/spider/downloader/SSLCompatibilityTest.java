package com.noob.spider.downloader;

import org.junit.Test;
import com.noob.spider.Page;
import com.noob.spider.Request;
import com.noob.spider.Site;
import com.noob.spider.Task;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 *         Date: 2017/11/29
 *         Time: 下午1:32
 */
public class SSLCompatibilityTest {

    @Test
    public void test_tls12() throws Exception {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Task task = Site.me().setCycleRetryTimes(5).toTask();
        Request request = new Request("https://juejin.im/");
        Page page = httpClientDownloader.download(request, task);
        assertThat(page.isDownloadSuccess()).isTrue();
    }
}
