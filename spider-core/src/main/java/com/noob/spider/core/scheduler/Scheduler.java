package com.noob.spider.core.scheduler;

import com.noob.spider.core.Request;
import com.noob.spider.core.Task;

/**
 * Scheduler is the part of url manage.<br>
 * You can implement interface Scheduler to do:
 * manage urls to fetch
 * remove duplicate urls
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public interface Scheduler {

    /**
     * add a url to fetch
     *
     * @param request request
     * @param task task
     */
    public void push(Request request, Task task);

    /**
     * get an url to crawl
     *
     * @param task the task of spider
     * @return the url to crawl
     */
    public Request poll(Task task);

}

// 2019-06-26 07：56 可以看到 Scheduler 并不是“调度”系统，而是定位在“待爬取队列”这样的角色