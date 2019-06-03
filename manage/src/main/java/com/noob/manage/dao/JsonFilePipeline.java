package com.noob.manage.dao;

import com.google.gson.Gson;
import com.noob.manage.model.commons.Webpage;
import com.noob.spider.ResultItems;
import com.noob.spider.Task;
import com.noob.spider.pipeline.Pipeline;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;

/**
 * Created by gsh199449 on 2016/12/8.
 */
@Component
public class JsonFilePipeline implements Pipeline {
    private final static Logger LOG = LogManager.getLogger(JsonFilePipeline.class);
    private final static Gson gson = new Gson();

    @Override
    public void process(ResultItems resultItems, Task task) {
        Webpage webpage = CommonWebpagePipeline.convertResultItems2Webpage(resultItems);
        try {
            FileUtils.writeStringToFile(
                    new File("gather_platform_data/" + webpage.getSpiderUUID() + ".json"),
                    gson.toJson(webpage) + "\n",
                    true);
        } catch (IOException e) {
            LOG.error("序列化网页信息出错,{}", e.getLocalizedMessage());
        }
    }
}

