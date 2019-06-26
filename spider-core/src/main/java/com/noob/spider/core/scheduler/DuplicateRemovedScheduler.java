package com.noob.spider.core.scheduler;

import com.noob.spider.core.scheduler.component.DuplicateRemover;
import com.noob.spider.core.scheduler.component.HashSetDuplicateRemover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.noob.spider.core.Request;
import com.noob.spider.core.Task;
import com.noob.spider.core.utils.HttpConstant;

/**
 * Remove duplicate urls and only push urls which are not duplicate.<br><br>
 *
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public abstract class DuplicateRemovedScheduler implements Scheduler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private DuplicateRemover duplicatedRemover = new HashSetDuplicateRemover();

    public DuplicateRemover getDuplicateRemover() {
        return duplicatedRemover;
    }

    public DuplicateRemovedScheduler setDuplicateRemover(DuplicateRemover duplicatedRemover) {
        this.duplicatedRemover = duplicatedRemover;
        return this;
    }

    @Override
    public void push(Request request, Task task) {
        logger.trace("get a candidate url {}", request.getUrl());
        if (shouldReserved(request) || noNeedToRemoveDuplicate(request) || !duplicatedRemover.isDuplicate(request, task)) {
            logger.debug("push to queue {}", request.getUrl());
            pushWhenNoDuplicate(request, task);
        }
    }

    protected boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
    }

    protected boolean noNeedToRemoveDuplicate(Request request) {
        return HttpConstant.Method.POST.equalsIgnoreCase(request.getMethod());
    }

    protected void pushWhenNoDuplicate(Request request, Task task) {

    }
}

// 2019-06-26 8:08
// 通过实现接口，做了一个抽象类：如果 url 是重复的，就不会重复的添加了
// 这里使用抽象类的高明之处是，在接口的基础上，进一步指定了接口的push方法的逻辑
// 但是并没有限制具体使用什么基础结构来实现，给子类预留了发挥的空间