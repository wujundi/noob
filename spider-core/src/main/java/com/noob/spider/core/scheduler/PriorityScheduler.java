package com.noob.spider.core.scheduler;

import com.noob.spider.core.Request;
import com.noob.spider.core.Task;
import com.noob.spider.core.utils.NumberUtils;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Priority scheduler. Request with higher priority will poll earlier. <br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.1
 */
public class PriorityScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler {

    public static final int INITIAL_CAPACITY = 5;

    private BlockingQueue<Request> noPriorityQueue = new LinkedBlockingQueue<Request>();

    private PriorityBlockingQueue<Request> priorityQueuePlus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -NumberUtils.compareLong(o1.getPriority(), o2.getPriority());
        }
    });

    private PriorityBlockingQueue<Request> priorityQueueMinus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -NumberUtils.compareLong(o1.getPriority(), o2.getPriority());
        }
    });

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        if (request.getPriority() == 0) {
            noPriorityQueue.add(request);
        } else if (request.getPriority() > 0) {
            priorityQueuePlus.put(request);
        } else {
            priorityQueueMinus.put(request);
        }
    }

    @Override
    public synchronized Request poll(Task task) {
        // 优先从高优先级（正向优先级）队列中取数
        Request poll = priorityQueuePlus.poll();
        if (poll != null) {
            return poll;
        }
        // 高优先级取完之后从普通队列中取
        poll = noPriorityQueue.poll();
        if (poll != null) {
            return poll;
        }
        // 上述两个都取完之后，再到低优先级（负向优先级）队列中取数
        return priorityQueueMinus.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return noPriorityQueue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }
}

// 2019-06-27 19:43 通过 java 原生的优先级队列 + 自己定义一个分界线
// 来实现不同优先级的队列，在弹出时候的先后顺序