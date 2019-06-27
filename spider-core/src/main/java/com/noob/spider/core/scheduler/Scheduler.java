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

// 2019-06-27 同样，Scheduler 作为一个借口，还是“概述性”的提出需要做的事情而已
