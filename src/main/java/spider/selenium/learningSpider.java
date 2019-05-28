package spider.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * https://3g.learning.gov.cn/
 */
public class learningSpider {

    public static void main(String[] args) {

        // 设置 chrome driver
        System.setProperty("webdriver.chrome.driver", "/Users/wujundi/chromedriver");

        //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
        WebDriver driver =new ChromeDriver();

        // 首先进一下网站
        driver.get("https://3g.learning.gov.cn/study/");

        // 替换 session id
        driver.manage().deleteCookieNamed("SESSID");
        Cookie c1 = new Cookie("SESSID","00d98115b00809b251be3d6f2e9a04c7");
        driver.manage().addCookie(c1);



        //打开指定的网站
        driver.get("https://3g.learning.gov.cn/study/choose_course.php");

        //匹配第一页搜索结果的标题， 循环打印
        List<WebElement> search_result = driver.findElements(By.name("mid[]"));


        //打印元素的个数
        System.out.println(search_result.size());

        // 循环拿到所有的 课程id
        for(int i=2; i<=126; i++){
            driver.get("https://3g.learning.gov.cn/study/choose_course.php?offset="+i+"&&coursetype=&examtype=&coursename=");
            //匹配第一页搜索结果的标题， 循环打印
            search_result = driver.findElements(By.name("mid[]"));
            // 循环打印搜索结果的标题
            System.out.println("-------我是分割线---------");
            for(WebElement result : search_result){
                System.out.println(result.getAttribute("value"));
            }
            System.out.println("-------我是分割线---------");
        }

        driver.quit();//退出浏览器
    }
}
