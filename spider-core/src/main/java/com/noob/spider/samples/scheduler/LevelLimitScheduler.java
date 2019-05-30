package com.noob.spider.samples.scheduler;

import com.noob.spider.scheduler.PriorityScheduler;
import com.noob.spider.Request;
import com.noob.spider.Task;

/**
 * @author code4crafter@gmail.com
 */
public class LevelLimitScheduler extends PriorityScheduler {

    private int levelLimit = 3;

    public LevelLimitScheduler(int levelLimit) {
        this.levelLimit = levelLimit;
    }

    @Override
    public synchronized void push(Request request, Task task) {
        if (((Integer) request.getExtra("_level")) <= levelLimit) {
            super.push(request, task);
        }
    }
}
