package com.noob.spider.extension.utils;

import com.noob.spider.core.selector.CssSelector;
import com.noob.spider.core.selector.JsonPathSelector;
import com.noob.spider.core.selector.RegexSelector;
import com.noob.spider.core.selector.Selector;
import com.noob.spider.core.selector.XpathSelector;
import com.noob.spider.extension.model.annotation.ExtractBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Tools for annotation converting. <br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.1
 */
public class ExtractorUtils {

    public static com.noob.spider.core.selector.Selector getSelector(ExtractBy extractBy) {
        String value = extractBy.value();
        com.noob.spider.core.selector.Selector selector;
        switch (extractBy.type()) {
            case Css:
                selector = new CssSelector(value);
                break;
            case Regex:
                selector = new RegexSelector(value);
                break;
            case XPath:
                selector = new com.noob.spider.core.selector.XpathSelector(value);
                break;
            case JsonPath:
                selector = new JsonPathSelector(value);
                break;
            default:
                selector = new XpathSelector(value);
        }
        return selector;
    }

    public static List<com.noob.spider.core.selector.Selector> getSelectors(ExtractBy[] extractBies) {
        List<com.noob.spider.core.selector.Selector> selectors = new ArrayList<Selector>();
        if (extractBies == null) {
            return selectors;
        }
        for (ExtractBy extractBy : extractBies) {
            selectors.add(getSelector(extractBy));
        }
        return selectors;
    }
}
