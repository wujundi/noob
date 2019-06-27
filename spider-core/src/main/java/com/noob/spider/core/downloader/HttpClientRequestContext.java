package com.noob.spider.core.downloader;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/4/8
 *         Time: 19:43
 * @since 0.7.0
 */
public class HttpClientRequestContext {

    private HttpUriRequest httpUriRequest;

    private HttpClientContext httpClientContext;

    public HttpUriRequest getHttpUriRequest() {
        return httpUriRequest;
    }

    public void setHttpUriRequest(HttpUriRequest httpUriRequest) {
        this.httpUriRequest = httpUriRequest;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public void setHttpClientContext(HttpClientContext httpClientContext) {
        this.httpClientContext = httpClientContext;
    }

}

// 2019-06-27 22：40 这个类就是把 HttpUriRequest 和 HttpClientContext 属性打包在一起的类
// HttpUriRequest 是干啥的？
// HttpClientContext 是干啥的？








