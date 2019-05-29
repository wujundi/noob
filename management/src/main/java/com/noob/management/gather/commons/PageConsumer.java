package com.noob.management.gather.commons;

import com.noob.management.model.async.Task;
import com.noob.management.model.commons.SpiderInfo;
import us.codecraft.webmagic.Page;

/**
 * PageConsumer
 *
 * @author Gao Shen
 * @version 16/7/8
 */
@FunctionalInterface
public interface PageConsumer {
    void accept(Page page, SpiderInfo info, Task task);
}
