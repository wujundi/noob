package com.noob.manage.gather.commons;

import com.noob.manage.model.async.Task;
import com.noob.manage.model.commons.SpiderInfo;
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
