package com.noob.spider.example;

import com.noob.spider.downloader.MockGithubDownloader;
import org.junit.Test;
import com.noob.spider.ResultItems;
import com.noob.spider.Spider;
import com.noob.spider.Task;
import com.noob.spider.pipeline.Pipeline;
import com.noob.spider.processor.example.GithubRepoPageProcessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/1/19
 *         Time: 上午7:27
 */
public class GithubRepoPageProcessorTest {

    @Test
    public void test_github() throws Exception {
        Spider.create(new GithubRepoPageProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                assertThat(((String) resultItems.get("name")).trim()).isEqualTo("com/noob/spider");
                assertThat(((String) resultItems.get("author")).trim()).isEqualTo("code4craft");
            }
        }).setDownloader(new MockGithubDownloader()).test("https://github.com/code4craft/webmagic");
    }
}
