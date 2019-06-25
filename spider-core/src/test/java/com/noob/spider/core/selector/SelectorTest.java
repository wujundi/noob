package com.noob.spider.core.selector;

import com.noob.spider.core.selector.Html;
import com.noob.spider.core.selector.Selectable;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 */
public class SelectorTest {

    private String html = "<div><a href='http://whatever.com/aaa'></a></div><div><a href='http://whatever.com/bbb'></a></div>";

    @Test
    public void testChain() throws Exception {
        com.noob.spider.core.selector.Html selectable = new com.noob.spider.core.selector.Html(html);
        List<String> linksWithoutChain = selectable.links().all();
        com.noob.spider.core.selector.Selectable xpath = selectable.xpath("//div");
        List<String> linksWithChainFirstCall = xpath.links().all();
        List<String> linksWithChainSecondCall = xpath.links().all();
        assertThat(linksWithoutChain).hasSameSizeAs(linksWithChainFirstCall);
        assertThat(linksWithChainFirstCall).hasSameSizeAs(linksWithChainSecondCall);
    }

    @Test
    public void testNodes() throws Exception {
        com.noob.spider.core.selector.Html selectable = new Html(html);
        List<Selectable> links = selectable.xpath("//a").nodes();
        assertThat(links.get(0).links().get()).isEqualTo("http://whatever.com/aaa");
    }
}
