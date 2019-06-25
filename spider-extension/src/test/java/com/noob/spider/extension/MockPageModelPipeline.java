package com.noob.spider.extension;

import com.noob.spider.core.Task;
import com.noob.spider.extension.pipeline.PageModelPipeline;
import junit.framework.Assert;

/**
 * @author code4crafter@gmail.com
 */
public class MockPageModelPipeline implements PageModelPipeline {
    @Override
    public void process(Object o, Task task) {
        Assert.assertNotNull(o);
    }
}
