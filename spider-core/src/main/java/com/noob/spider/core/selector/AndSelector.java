package com.noob.spider.core.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * All selectors will be arranged as a pipeline. <br>
 * The next selector uses the result of the previous as source.
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class AndSelector implements Selector {

    private List<Selector> selectors = new ArrayList<Selector>();

    public AndSelector(Selector... selectors) {
        for (Selector selector : selectors) {
            this.selectors.add(selector);
        }
    }

    public AndSelector(List<Selector> selectors) {
        this.selectors = selectors;
    }

    @Override
    public String select(String text) {
        for (Selector selector : selectors) {
            if (text == null) {
                return null;
            }
            text = selector.select(text);
        }
        return text;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> results = new ArrayList<String>();
        boolean first = true;
        for (Selector selector : selectors) {
            if (first) {
                results = selector.selectList(text);
                first = false;
            } else { // 如果不是首次调用，那么就将结果集中的数据作为输入，进行下一轮
                List<String> resultsTemp = new ArrayList<String>();
                for (String result : results) {
                    resultsTemp.addAll(selector.selectList(result));
                }
                results = resultsTemp;
                if (results == null || results.size() == 0) {
                    return results;
                }
            }
        }
        return results;
    }
}

// 2019-07-08 23：17