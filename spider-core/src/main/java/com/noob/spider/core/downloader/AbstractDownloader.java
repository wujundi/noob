package com.noob.spider.core.downloader;

import com.noob.spider.core.Page;
import com.noob.spider.core.Request;
import com.noob.spider.core.Site;
import com.noob.spider.core.selector.Html;

/**
 * Base class of downloader with some common methods.
 *
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public abstract class AbstractDownloader implements Downloader {

    /**
     * A simple method to download a url.
     *
     * @param url url
     * @return html
     */
    public Html download(String url) {
        return download(url, null);
    }

    /**
     * A simple method to download a url.
     *
     * @param url url
     * @param charset charset
     * @return html
     */
    public Html download(String url, String charset) {
        Page page = download(new Request(url), Site.me().setCharset(charset).toTask());
        return (Html) page.getHtml();
    }

    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }

}

// 2019-04-16
// 2019-06-26 8:13 作为一个抽象类，这里也只是很潦草的实现了一下 download 方法，感觉意义不算太大