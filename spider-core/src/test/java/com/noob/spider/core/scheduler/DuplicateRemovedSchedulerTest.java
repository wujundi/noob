package com.noob.spider.core.scheduler;

import com.noob.spider.core.Request;
import com.noob.spider.core.scheduler.component.DuplicateRemover;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.noob.spider.core.Task;
import com.noob.spider.core.utils.HttpConstant;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/3/11
 *         Time: 上午11:26
 */
@RunWith(MockitoJUnitRunner.class)
public class DuplicateRemovedSchedulerTest {

    private com.noob.spider.core.scheduler.DuplicateRemovedScheduler duplicateRemovedScheduler = new DuplicateRemovedScheduler() {
        @Override
        public Request poll(Task task) {
            return null;
        }
    };

    @Test
    public void test_no_duplicate_removed_for_post_request() throws Exception {
        DuplicateRemover duplicateRemover = Mockito.mock(DuplicateRemover.class);
        duplicateRemovedScheduler.setDuplicateRemover(duplicateRemover);
        Request request = new Request("https://www.google.com/");
        request.setMethod(HttpConstant.Method.POST);
        duplicateRemovedScheduler.push(request, null);
        verify(duplicateRemover,times(0)).isDuplicate(any(Request.class),any(Task.class));
    }

    @Test
    public void test_duplicate_removed_for_get_request() throws Exception {
        DuplicateRemover duplicateRemover = Mockito.mock(DuplicateRemover.class);
        duplicateRemovedScheduler.setDuplicateRemover(duplicateRemover);
        Request request = new Request("https://www.google.com/");
        request.setMethod(HttpConstant.Method.GET);
        duplicateRemovedScheduler.push(request, null);
        verify(duplicateRemover,times(1)).isDuplicate(any(Request.class),any(Task.class));
    }
}
