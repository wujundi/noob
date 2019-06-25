package com.noob.spider.extension.model;

import com.noob.spider.extension.model.annotation.ExtractBy;

/**
 * @author code4crafter@gmail.com
 */
public class BaseRepo {

    @ExtractBy("//ul[@class='pagehead-actions']/li[1]//a[@class='social-count js-social-count']/text()")
    protected int star;
}
