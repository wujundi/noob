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

// 2019-06-27 18:16 是套路太一致还是我在家里看过忘记push了？
// 按照【接口->抽象类->具体实现】的套路，
// 接口用来描绘最基本的动作
// 抽象类实现接口，在接口的基础上，对于如何实现这样的功能做出基本的规划
// 具体的实现类，继承抽象类，并且使用类库或组件，实现抽象类中规划的方法
// 简言之：接口是对外的精简承诺；抽象类是实现这些承诺的高层次抽象的方法论；具体的实现类是落实这些方法论的最终逻辑

