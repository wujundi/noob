package com.noob.spider.downloader;

import org.junit.Test;
import com.noob.spider.Request;
import com.noob.spider.Site;
import com.noob.spider.utils.UrlUtils;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 *         Date: 2017/7/22
 *         Time: 下午5:29
 */
public class HttpUriRequestConverterTest {

    @Test
    public void test_illegal_uri_correct() throws Exception {
        HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(new Request(UrlUtils.fixIllegalCharacterInUrl("http://bj.zhongkao.com/beikao/yimo/##")), Site.me(), null);
        assertThat(requestContext.getHttpUriRequest().getURI()).isEqualTo(new URI("http://bj.zhongkao.com/beikao/yimo/#"));
    }
}