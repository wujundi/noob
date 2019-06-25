package com.noob.spider.core.scheduler;

import com.noob.spider.core.Request;
import com.noob.spider.core.Site;
import com.noob.spider.core.Task;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author code4crafter@gmail.com <br>
 */
public class PrioritySchedulerTest {

    private com.noob.spider.core.scheduler.PriorityScheduler priorityScheduler = new PriorityScheduler();

    private Task task = new Task() {
        @Override
        public String getUUID() {
            return "1";
        }

        @Override
        public Site getSite() {
            return null;
        }
    };

    @Test
    public void testDifferentPriority() {
        Request request = new Request("a");
        request.setPriority(100);
        priorityScheduler.push(request,task);

        request = new Request("b");
        request.setPriority(900);
        priorityScheduler.push(request,task);

        request = new Request("c");
        priorityScheduler.push(request,task);

        request = new Request("d");
        request.setPriority(-900);
        priorityScheduler.push(request,task);

        Request poll = priorityScheduler.poll(task);
        Assert.assertEquals("b",poll.getUrl());
        poll = priorityScheduler.poll(task);
        Assert.assertEquals("a",poll.getUrl());
        poll = priorityScheduler.poll(task);
        Assert.assertEquals("c",poll.getUrl());
        poll = priorityScheduler.poll(task);
        Assert.assertEquals("d",poll.getUrl());
    }

    @Test
    public void testNoPriority() {
        Request request = new Request("a");
        priorityScheduler.push(request,task);

        request = new Request("b");
        priorityScheduler.push(request,task);

        request = new Request("c");
        priorityScheduler.push(request,task);

        Request poll = priorityScheduler.poll(task);
        Assert.assertEquals("a",poll.getUrl());

        poll = priorityScheduler.poll(task);
        Assert.assertEquals("b",poll.getUrl());

        poll = priorityScheduler.poll(task);
        Assert.assertEquals("c",poll.getUrl());
    }
}
