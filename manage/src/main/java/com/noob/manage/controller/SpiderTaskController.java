package com.noob.manage.controller;

import com.noob.manage.model.commons.Webpage;
import com.noob.manage.model.utils.ResultBundle;
import com.noob.manage.model.utils.ResultListBundle;
import com.noob.manage.service.AsyncGatherService;
import com.noob.manage.service.SpiderTaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * CommonsWebpageDownloadController
 *
 * @author Gao Shen
 * @version 16/4/8
 */
@Controller
//@RequestMapping("/commons/spider")
@RequestMapping("/")
public class SpiderTaskController extends AsyncGatherBaseController {
    private Logger LOG = LogManager.getLogger(SpiderTaskController.class);
    private SpiderTaskService spiderService;

    @Autowired
    public SpiderTaskController(@Qualifier("spiderTaskService") AsyncGatherService asyncGatherService) {
        super(asyncGatherService);
        this.spiderService = (SpiderTaskService) asyncGatherService;
    }

    /**
     * 启动爬虫
     *
     * @param spiderInfoJson 使用json格式进行序列化的spiderinfo
     * @return 任务id
     */
    @RequestMapping(value = "/commons/spider/start", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> start(String spiderInfoJson) {
        return spiderService.start(spiderInfoJson);
    }

    /**
     * 停止爬虫
     *
     * @param uuid 任务id(爬虫uuid)
     * @return
     */
    @RequestMapping(value = "/commons/spider/stop", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stop(String uuid) {
        return spiderService.stop(uuid);
    }

    /**
     * 获取爬虫运行时信息
     *
     * @param uuid 爬虫uuid 任务id
     * @return
     */
    @RequestMapping(value = "/commons/spider/runtimeInfo", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Map<Object, Object>> runtimeInfo(String uuid, @RequestParam(value = "containsExtraInfo", required = false, defaultValue = "false") boolean containsExtraInfo) {
        return spiderService.runtimeInfo(uuid, containsExtraInfo);
    }

    /**
     * 列出所有爬虫的运行时信息
     *
     * @return
     */
    @RequestMapping(value = "/commons/spider/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Map<String, Map<Object, Object>>> list(@RequestParam(value = "containsExtraInfo", required = false, defaultValue = "false") boolean containsExtraInfo) {
        return spiderService.list(containsExtraInfo);
    }

    /**
     * 删除爬虫
     *
     * @param uuid 爬虫uuid 任务id
     * @return
     */
    @RequestMapping(value = "/commons/spider/delete", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> delete(String uuid) {
        return spiderService.delete(uuid);
    }

    /**
     * 删除所有爬虫
     *
     * @return
     */
    @RequestMapping(value = "/commons/spider/deleteAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> deleteAll() {
        return spiderService.deleteAll();
    }

    /**
     * 测试爬虫模板
     *
     * 对应页面上的 “抓取样例数据” ， 2019-06-20 8：22
     * @param spiderInfoJson
     * @return
     */
    @RequestMapping(value = "/commons/spider/testSpiderInfo", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultListBundle<Webpage> testSpiderInfo(String spiderInfoJson) {
        return spiderService.testSpiderInfo(spiderInfoJson);
    }

    /**
     * 获取忽略url黑名单
     *
     * @return
     */
    @RequestMapping(value = "/commons/spider/getIgnoredUrls", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultListBundle<String> getIgnoredUrls() {
        return spiderService.getIgnoredUrls();
    }

    /**
     * 添加忽略url黑名单
     *
     * @param postfix
     */
    @RequestMapping(value = "/commons/spider/addIgnoredUrl", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> addIgnoredUrl(String postfix) {
        return spiderService.addIgnoredUrl(postfix);
    }

    /**
     * 根据爬虫模板ID批量启动任务
     *
     * @param spiderInfoIdList 爬虫模板ID列表
     * @return 任务id列表
     */
    @RequestMapping(value = "/commons/spider/startAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultListBundle<String> startAll(String spiderInfoIdList) {
        return spiderService.startAll(Lists.newArrayList(spiderInfoIdList.split(",")));
    }

    @RequestMapping(value = "/commons/spider/createQuartzJob", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> createQuartzJob(String spiderInfoId, int hoursInterval) {
        return spiderService.createQuartzJob(spiderInfoId, hoursInterval);
    }

    @RequestMapping(value = "/commons/spider/removeQuartzJob", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> removeQuartzJob(String spiderInfoId) {
        return spiderService.removeQuartzJob(spiderInfoId);
    }

    @RequestMapping(value = "/commons/spider/checkQuartzJob", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String checkQuartzJob(String spiderInfoId) {
        return spiderService.checkQuartzJob(spiderInfoId).getResult();
    }

    @RequestMapping(value = "/commons/spider/exportQuartz", method = RequestMethod.GET, produces = "application/json")
    public void exportQuartz(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=commons-spider.quartz");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(spiderService.exportQuartz().getBytes());
        outputStream.close();
    }

    @RequestMapping(value = "/commons/spider/importQuartz", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void importQuartz(String json) {
        spiderService.importQuartz(json);
    }
}

// 2019-07-22 19:26 这个 controller 是爬虫任务基础动作？

