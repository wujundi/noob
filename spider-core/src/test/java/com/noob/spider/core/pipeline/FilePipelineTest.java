package com.noob.spider.core.pipeline;

import com.noob.spider.core.Request;
import com.noob.spider.core.ResultItems;
import com.noob.spider.core.Site;
import com.noob.spider.core.Task;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by ywooer on 2014/5/6 0006.
 */
public class FilePipelineTest {

    private static ResultItems resultItems;
    private static Task task;

    @BeforeClass
    public static void before() {
        resultItems = new ResultItems();
        resultItems.put("content", "webmagic 爬虫工具");
        Request request = new Request("http://www.baidu.com");
        resultItems.setRequest(request);

        task = new Task() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public Site getSite() {
                return null;
            }
        };
    }
    @Test
    public void testProcess() {
        com.noob.spider.core.pipeline.FilePipeline filePipeline = new FilePipeline();
        filePipeline.process(resultItems, task);
    }
}
