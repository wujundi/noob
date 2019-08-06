package com.noob.manage.gather.async.quartz;

import com.noob.manage.model.commons.SpiderInfo;
import com.noob.manage.service.SpiderTaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Created by gaoshen on 2017/1/18.
 */
@DisallowConcurrentExecution
public class WebpageSpiderJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(WebpageSpiderJob.class);
    private SpiderInfo spiderInfo;
    private SpiderTaskService spiderTaskService;

    public WebpageSpiderJob setSpiderTaskService(SpiderTaskService spiderTaskService) {
        this.spiderTaskService = spiderTaskService;
        return this;
    }

    public WebpageSpiderJob setSpiderInfo(SpiderInfo spiderInfo) {
        this.spiderInfo = spiderInfo;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("开始定时网页采集任务，网站：{}，模板ID：{}", spiderInfo.getSiteName(), spiderInfo.getId());
        String uuid = spiderTaskService.start(spiderInfo).getResult();
        LOG.info("定时网页采集任务完成，网站：{}，模板ID：{},任务ID：{}", spiderInfo.getSiteName(), spiderInfo.getId(), uuid);
    }
}
