package com.noob.spider.extension;

import com.noob.spider.core.Page;
import com.noob.spider.core.Request;
import com.noob.spider.core.Site;
import com.noob.spider.core.downloader.HttpClientDownloader;
import com.noob.spider.extension.model.PageMapper;
import com.noob.spider.core.proxy.ProxyProvider;

/**
 * @author code4crafter@gmail.com
 *         Date: 2017/5/27
 * @since 0.7.0
 */
public class SimpleHttpClient {

    private final HttpClientDownloader httpClientDownloader;

    private final com.noob.spider.core.Site site;

    public SimpleHttpClient() {
        this(com.noob.spider.core.Site.me());
    }

    public SimpleHttpClient(Site site) {
        this.site = site;
        this.httpClientDownloader = new HttpClientDownloader();
    }

    public void setProxyProvider(ProxyProvider proxyProvider){
        this.httpClientDownloader.setProxyProvider(proxyProvider);
    }

    public <T> T get(String url, Class<T> clazz) {
        return get(new com.noob.spider.core.Request(url), clazz);
    }

    public <T> T get(com.noob.spider.core.Request request, Class<T> clazz) {
        com.noob.spider.core.Page page = httpClientDownloader.download(request, site.toTask());
        if (!page.isDownloadSuccess()) {
            return null;
        }
        return new PageMapper<T>(clazz).get(page);
    }

    public com.noob.spider.core.Page get(String url) {
        return httpClientDownloader.download(new com.noob.spider.core.Request(url), site.toTask());
    }

    public Page get(Request request) {
        return httpClientDownloader.download(request, site.toTask());
    }

}
