package com.noob.spider.core.scheduler;

import com.noob.spider.core.Task;

/**
 * The scheduler whose requests can be counted for monitor.
 *
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public interface MonitorableScheduler extends Scheduler {

    public int getLeftRequestsCount(Task task);

    public int getTotalRequestsCount(Task task);

}

// 2019-06-27 18:09 这是个继承接口的接口，其目的是在 Scheduler 接口的基础上进行添加对于监控操作的基本描述