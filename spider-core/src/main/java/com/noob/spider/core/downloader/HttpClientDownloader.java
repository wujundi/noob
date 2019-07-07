package com.noob.spider.core.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.noob.spider.core.Page;
import com.noob.spider.core.Request;
import com.noob.spider.core.Site;
import com.noob.spider.core.Task;
import com.noob.spider.core.proxy.Proxy;
import com.noob.spider.core.proxy.ProxyProvider;
import com.noob.spider.core.selector.PlainText;
import com.noob.spider.core.utils.CharsetUtils;
import com.noob.spider.core.utils.HttpClientUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * The http downloader based on HttpClient.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class HttpClientDownloader extends AbstractDownloader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    
    private ProxyProvider proxyProvider;

    private boolean responseHeader = true;

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    /**
     * 主要思路是，通过Site获取域名，然后通过域名判断是否在httpClients这个map中已存在HttpClient实例，
     *         如果存在则重用，
     *         否则通过httpClientGenerator创建一个新的实例，然后加入到httpClients这个map中，并返回。
     *         注意为了确保线程安全性，这里用到了线程安全的双重判断机制。
     * @param site
     * @return
     */
    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        // 这块这个双重检查锁仔细理解下
        // 2019-07-07 20:59 （1）
        if (httpClient == null) {
            // A，B 两个线程进入此处
            // 当前 A 获得锁，B 等待A放锁后，将获得锁
            synchronized (this) {
                httpClient = httpClients.get(domain);
                // （2）
                // 这里面如果不做判断，那么依次获得锁的线程，都将执行代码块的内容，造成线程不安全
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }
    /*
    原始版本是整个方法加锁 ，即 private synchronized CloseableHttpClient getHttpClient(Site site){...}
    改造成双重检查锁的动机是进一步缩小锁的范围，并且降低“抢了锁之后才发现，原来没啥要做的事”这种情况，以减小开销
    所以在（1）处的判断，是为了“回绝”大部分“无效(即便抢了锁，也不会执行什么有效代码)”的线程进入锁的
    而，在（2）处，则是对有幸进入（1）部分之后，轮候锁的那些线程，控制线程安全的地方
    具体的方式是，在锁的内部对状态进行判断，确保一个线程进行有效操作之后，对于轮流进入代码块的其他线程，不会重复进行操作
     */

    /**
     * 这个方法就是实际发送 http 请求的方法
     * @param request request
     * @param task task
     * @return
     */
    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        Page page = Page.fail();
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite().getCharset(), httpResponse, task);
            onSuccess(request);
            logger.info("downloading page success {}", request.getUrl());
            return page;
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, page, task);
            }
        }
    }

    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    /**
     * 将 http 返回的 httpResponse 封装为 Page 的逻辑
     * @param request
     * @param charset
     * @param httpResponse
     * @param task
     * @return
     * @throws IOException
     */
    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
        // 2019-07-07 22：03 这里有个疑问，为什么一定要转成 字节数组？
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        // null 值处理
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()){
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }
}

// 2019-07-07 22:04 调用 apache http 组件进行请求，并将返回的信息封装为 Page 的操作

