package com.noob.spider.model;

import com.noob.spider.downloader.MockGithubDownloader;
import org.junit.Test;
import com.noob.spider.Site;
import com.noob.spider.Task;
import com.noob.spider.example.GithubRepo;
import com.noob.spider.pipeline.PageModelPipeline;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author code4crafter@gmail.com <br>
 */
public class GithubRepoTest {

    @Test
    public void test() {
        OOSpider.create(Site.me().setSleepTime(0)
                , new PageModelPipeline<GithubRepo>() {
            @Override
            public void process(GithubRepo o, Task task) {
                assertThat(o.getStar()).isEqualTo(86);
                assertThat(o.getFork()).isEqualTo(70);
            }
        }, GithubRepo.class).addUrl("https://github.com/code4craft/webmagic").setDownloader(new MockGithubDownloader()).test("https://github.com/code4craft/webmagic");
    }

}
