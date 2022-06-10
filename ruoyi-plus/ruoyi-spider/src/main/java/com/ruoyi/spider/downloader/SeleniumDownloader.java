package com.ruoyi.spider.downloader;


import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.ruoyi.spider.domain.SpiderConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

public class SeleniumDownloader implements Downloader, Closeable {
    private volatile WebDriverPool webDriverPool;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int sleepTime = 0;
    private int poolSize = 1;
    private SpiderConfig spiderConfig;
    public SeleniumDownloader(String chromeDriverPath) {
        System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
    }
    public SeleniumDownloader(SpiderConfig spiderConfig) {
        this.spiderConfig=spiderConfig;
    }
    public SeleniumDownloader(String chromeDriverPath,SpiderConfig spiderConfig) {
        System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
        this.spiderConfig=spiderConfig;
    }
    public SeleniumDownloader() {
    }

    public SeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }
    @Override
    public Page download(Request request, Task task) {
        this.checkInit();

        WebDriver webDriver;
        try {
            webDriver = this.webDriverPool.get();
        } catch (InterruptedException var10) {
            this.logger.warn("interrupted", var10);
            return null;
        }

        this.logger.info("downloading page " + request.getUrl());

        webDriver.get(request.getUrl());
        try {
            Thread.sleep(task.getSite().getSleepTime());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Options manage = webDriver.manage();
        Site site = task.getSite();
        if (site.getCookies() != null) {
            Iterator iterator = site.getCookies().entrySet().iterator();

            while(iterator.hasNext()) {
                Entry<String, String> cookieEntry = (Entry)iterator.next();
                Cookie cookie = new Cookie((String)cookieEntry.getKey(), (String)cookieEntry.getValue());
                manage.addCookie(cookie);
            }
        }
        Long scrollHeight=(Long) ((JavascriptExecutor) webDriver).executeScript("return document.body.scrollHeight");
        Long height=300L;
        Long count=scrollHeight/height;
        //System.out.println("总计滚动："+count+"次！");
        for(int i=0;i<count;i++){
            Long start=(i-1)*height;
            Long end=i*height;
            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo("+start+", "+end+")");
            //System.out.println("window.scrollTo("+start+", "+end+")");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");//移动到页面最底部
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Page page = new Page();
        page.setRawText(content);
        page.setHtml(new Html(content, request.getUrl()));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        this.webDriverPool.returnToPool(webDriver);
        return page;
    }

    private void checkInit() {
        if (this.webDriverPool == null) {
            synchronized(this) {
                this.webDriverPool = new WebDriverPool(this.poolSize);
            }
        }

    }

    @Override
    public void setThread(int thread) {
        this.poolSize = thread;
    }
    @Override
    public void close() throws IOException {
        this.webDriverPool.closeAll();
    }
}
