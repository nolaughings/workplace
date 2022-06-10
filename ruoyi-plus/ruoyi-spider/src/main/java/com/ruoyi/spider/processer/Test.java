package com.ruoyi.spider.processer;

import com.ruoyi.spider.downloader.SeleniumDownloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.FileNotFoundException;
import java.util.List;

public class Test implements PageProcessor {

    private static Site site = Site.me().setRetryTimes(3).setSleepTime(100);


    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws FileNotFoundException {
       /* System.setProperty("webdriver.chrome.driver",
                "d:\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://c.biancheng.net/redis/what-is-redis.html");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();*/
       /* Spider spider = new Spider(new Test());
        spider.addUrl("http://c.biancheng.net/redis/what-is-redis.html");
        spider.setDownloader(new SeleniumDownloader("d:\\chromedriver.exe"));
        spider.thread(1).run();*/

       String s1="1.0";
       String s2="1.1";
        System.out.println(s1.compareTo(s2));

    }
}
