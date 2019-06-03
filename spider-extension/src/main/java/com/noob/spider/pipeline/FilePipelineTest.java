package com.noob.spider.pipeline;

import org.junit.BeforeClass;
import org.junit.Test;
import com.noob.spider.Request;
import com.noob.spider.ResultItems;
import com.noob.spider.Site;
import com.noob.spider.Task;

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
        FilePipeline filePipeline = new FilePipeline();
        filePipeline.process(resultItems, task);
    }
}
