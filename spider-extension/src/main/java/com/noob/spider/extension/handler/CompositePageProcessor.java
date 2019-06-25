package com.noob.spider.extension.handler;

import com.noob.spider.core.Page;
import com.noob.spider.core.Site;
import com.noob.spider.core.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 */
public class CompositePageProcessor implements PageProcessor {

    private Site site;

    private List<SubPageProcessor> subPageProcessors = new ArrayList<SubPageProcessor>();

    public CompositePageProcessor(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        for (SubPageProcessor subPageProcessor : subPageProcessors) {
            if (subPageProcessor.match(page.getRequest())) {
                SubPageProcessor.MatchOther matchOtherProcessorProcessor = subPageProcessor.processPage(page);
                if (matchOtherProcessorProcessor == null || matchOtherProcessorProcessor != SubPageProcessor.MatchOther.YES) {
                    return;
                }
            }
        }
    }

    public CompositePageProcessor setSite(Site site) {
        this.site = site;
        return this;
    }

    public CompositePageProcessor addSubPageProcessor(SubPageProcessor subPageProcessor) {
        this.subPageProcessors.add(subPageProcessor);
        return this;
    }

    public CompositePageProcessor setSubPageProcessors(SubPageProcessor... subPageProcessors) {
        this.subPageProcessors = new ArrayList<SubPageProcessor>();
        for (SubPageProcessor subPageProcessor : subPageProcessors) {
            this.subPageProcessors.add(subPageProcessor);
        }
        return this;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
