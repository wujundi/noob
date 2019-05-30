package com.noob.spider;

import junit.framework.Assert;
import com.noob.spider.pipeline.PageModelPipeline;

/**
 * @author code4crafter@gmail.com
 */
public class MockPageModelPipeline implements PageModelPipeline{
    @Override
    public void process(Object o, Task task) {
        Assert.assertNotNull(o);
    }
}
