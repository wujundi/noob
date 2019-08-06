package com.noob.manage.controller;

import com.google.gson.Gson;
import com.noob.manage.model.commons.SpiderInfo;
import com.noob.manage.model.utils.ResultBundle;
import com.noob.manage.model.utils.ResultListBundle;
import com.noob.manage.service.SpiderInfoService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * SpiderInfoController
 *
 * @author Gao Shen
 * @version 16/7/18
 */
//@RequestMapping("/commons/spiderinfo")
@RequestMapping("/")
@RestController
public class SpiderInfoController {
    private final static Logger LOG = LogManager.getLogger(SpiderInfoController.class);
    @Autowired
    private SpiderInfoService spiderInfoService;
    private Gson gson = new Gson();

    /**
     * 列出库中所有爬虫模板
     *
     * @param size 页面容量
     * @param page 页码
     * @return 爬虫模板列表
     */
    @RequestMapping(value = "/commons/spiderinfo/listAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultListBundle<SpiderInfo> listAll(@RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        return spiderInfoService.listAll(size, page);
    }

    /**
     * 根据domain获取结果
     *
     * @param domain 网站域名
     * @param size   每页数量
     * @param page   页码
     * @return 爬虫模板
     */
    @RequestMapping(value = "/commons/spiderinfo/getByDomain", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultListBundle<SpiderInfo> getByDomain(String domain, @RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        return spiderInfoService.getByDomain(domain, size, page);
    }

    /**
     * 根据网站domain删除数据
     *
     * @param domain 网站域名
     * @return 是否全部数据删除成功
     */
    @RequestMapping(value = "/commons/spiderinfo/deleteByDomain", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> deleteByDomain(String domain) {
        return spiderInfoService.deleteByDomain(domain);
    }

    /**
     * 根据id删除网页模板
     *
     * @param id 网页模板id
     * @return 是否删除
     */
    @RequestMapping(value = "/commons/spiderinfo/deleteById", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> deleteById(String id) {
        return spiderInfoService.deleteById(id);
    }

    /**
     * 存储模板
     *
     * @param spiderInfoJson 使用json格式进行序列化的spiderinfo
     * @return 模板id
     */
    @RequestMapping(value = "/commons/spiderinfo/save", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> save(String spiderInfoJson) {
        return spiderInfoService.index(gson.fromJson(spiderInfoJson, SpiderInfo.class));
    }


    /**
     * 编辑爬虫模板
     *
     * 将所有以 json 形式输入的参数，映射到 一个 spiderInfo 中, 2019-06-20 8:21
     *
     * @param jsonSpiderInfo json格式的爬虫模板
     * @return
     */
    @RequestMapping(value = "panel/commons/editSpiderInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView editSpiderInfo(String jsonSpiderInfo) {
        ModelAndView modelAndView = new ModelAndView("panel/commons/editSpiderInfo");
        if (StringUtils.isNotBlank(jsonSpiderInfo)) {
            SpiderInfo spiderInfo = gson.fromJson(jsonSpiderInfo, SpiderInfo.class);
            //对可能含有html的字段进行转义
            spiderInfo.setPublishTimeReg(StringEscapeUtils.escapeHtml4(spiderInfo.getPublishTimeReg()))
                    .setCategoryReg(StringEscapeUtils.escapeHtml4(spiderInfo.getCategoryReg()))
                    .setContentReg(StringEscapeUtils.escapeHtml4(spiderInfo.getContentReg()))
                    .setTitleReg(StringEscapeUtils.escapeHtml4(spiderInfo.getTitleReg()))
                    .setPublishTimeXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getPublishTimeXPath()))
                    .setCategoryXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getCategoryXPath()))
                    .setContentXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getContentXPath()))
                    .setTitleXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getTitleXPath()));
            for (SpiderInfo.FieldConfig config : spiderInfo.getDynamicFields()) {
                config.setRegex(StringEscapeUtils.escapeHtml4(config.getRegex()))
                        .setXpath(StringEscapeUtils.escapeHtml4(config.getXpath()));
            }
            modelAndView.addObject("spiderInfo", spiderInfo)
                    .addObject("jsonSpiderInfo", jsonSpiderInfo);
        } else {
            modelAndView.addObject("spiderInfo", new SpiderInfo());
        }
        return modelAndView;
    }

    /**
     * 编辑爬虫模板
     *
     * @param spiderInfoId 爬虫模板id
     * @return
     */
    @RequestMapping(value = "panel/commons/editSpiderInfoById", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView editSpiderInfoById(String spiderInfoId) {
        ModelAndView modelAndView = new ModelAndView("panel/commons/editSpiderInfo");
        SpiderInfo spiderInfo = spiderInfoService.getById(spiderInfoId).getResult();
        //对可能含有html的字段进行转义
        spiderInfo.setPublishTimeReg(StringEscapeUtils.escapeHtml4(spiderInfo.getPublishTimeReg()))
                .setCategoryReg(StringEscapeUtils.escapeHtml4(spiderInfo.getCategoryReg()))
                .setContentReg(StringEscapeUtils.escapeHtml4(spiderInfo.getContentReg()))
                .setTitleReg(StringEscapeUtils.escapeHtml4(spiderInfo.getTitleReg()))
                .setPublishTimeXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getPublishTimeXPath()))
                .setCategoryXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getCategoryXPath()))
                .setContentXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getContentXPath()))
                .setTitleXPath(StringEscapeUtils.escapeHtml4(spiderInfo.getTitleXPath()));

        for (SpiderInfo.FieldConfig config : spiderInfo.getDynamicFields()) {
            config.setRegex(StringEscapeUtils.escapeHtml4(config.getRegex()))
                    .setXpath(StringEscapeUtils.escapeHtml4(config.getXpath()));
        }
        modelAndView.addObject("spiderInfo", spiderInfo)
                .addObject("jsonSpiderInfo", gson.toJson(spiderInfo));
        return modelAndView;
    }

    @RequestMapping(value = "panel/commons/listSpiderInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView listSpiderInfo(String domain, @RequestParam(defaultValue = "1", required = false) int page) {
        ModelAndView modelAndView = new ModelAndView("panel/commons/listSpiderInfo");
        if (StringUtils.isBlank(domain)) {
            modelAndView.addObject("spiderInfoList", spiderInfoService.listAll(10, page).getResultList());
        } else {
            modelAndView.addObject("spiderInfoList", spiderInfoService.getByDomain(domain, 10, page).getResultList());
        }
        modelAndView.addObject("page", page)
                .addObject("domain", domain);
        return modelAndView;
    }

    @RequestMapping(value = "panel/commons/updateBySpiderInfoID", method = {RequestMethod.GET})
    public ModelAndView updateBySpiderInfoID(@RequestParam(required = false, defaultValue = "") String spiderInfoIdUpdateBy, @RequestParam(required = false, defaultValue = "") String spiderInfoJson) {
        ModelAndView modelAndView = new ModelAndView("panel/commons/updateBySpiderInfoID");
        modelAndView.addObject("spiderInfoJson", spiderInfoJson)
                .addObject("spiderInfoIdUpdateBy", spiderInfoIdUpdateBy);
        return modelAndView;
    }

}

// 2019-07-23 20:24 spiderInfo 系列，都是做与【爬虫模板】相关的事情