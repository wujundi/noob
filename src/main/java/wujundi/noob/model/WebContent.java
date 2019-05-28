package wujundi.noob.model;

public class WebContent {

    // 1、任务层面

    // 任务 id
    private String taskId;
    // 任务名称
    private String taskName;
    // 任务批次
    private String taskBatch;

    // 2、抓取层次层面

    // url id
    private String urlId;
    // url
    private String url;
    // keyword
    private String keyword;
    // 抓取时间
    private String crawlTime;
    // 抓取内容
    private String webContent;

    // 3、getter and setter

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskBatch() {
        return taskBatch;
    }

    public void setTaskBatch(String taskBatch) {
        this.taskBatch = taskBatch;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWebContent() {
        return webContent;
    }

    public void setWebContent(String webContent) {
        this.webContent = webContent;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime;
    }
}
