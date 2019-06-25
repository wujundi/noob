package com.noob.spider.extension.model;

import com.noob.spider.extension.model.annotation.ExtractBy;

/**
 * @author code4crafter@gmail.com
 *         Date: 2017/6/3
 *         Time: 下午9:07
 */
public class GithubRepoApi {

    @ExtractBy(type = ExtractBy.Type.JsonPath, value = "$.name",source = ExtractBy.Source.RawText)
    private String name;

    public String getName() {
        return name;
    }
}
