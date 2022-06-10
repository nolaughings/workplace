package com.ruoyi.spider.pipeline;

import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.common.utils.DownloadFileUtil;
import com.ruoyi.plugs.common.utils.FileUploadUtils;
import com.ruoyi.plugs.common.utils.thread.MultiThreadHandler;
import com.ruoyi.plugs.common.utils.thread.exception.ChildThreadException;
import com.ruoyi.plugs.common.utils.thread.parallel.ParallelTaskWithThreadPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class DownloadContentImagePipeline implements Pipeline {

    public static final String DOWNLOAD_CONTENT_IMG_FILE="_downloadContentImg";
    public static final String DOWNLOAD_CONTENT_IMG_PARAM="_downloadContentImgParam";
    /**
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> data = resultItems.getAll();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            String checkKey = key + DOWNLOAD_CONTENT_IMG_FILE;
            String checkKeyValue = String.valueOf(data.get(checkKey));
            if ("1".equals(checkKeyValue)) { //内容详情的图片需要下载到本地
                String content=String.valueOf(entry.getValue());
                if (StringUtils.isNotEmpty(content)) {
                    Document doc = Jsoup.parse(content);
                    if (doc != null) {
                        List<String> imgUrls = new ArrayList<String>();
                        Elements srcLinks = doc.select("img");
                        String baseUrl=resultItems.getRequest().getUrl().substring(0,resultItems.getRequest().getUrl().indexOf(task.getSite().getDomain()))+task.getSite().getDomain();
                        if(baseUrl.endsWith("/")){
                            baseUrl=baseUrl.substring(0,baseUrl.length()-1);
                        }
                        for (Element link : srcLinks) {
                            //:剔除标签，只剩链接路径
                            String imagesPath = link.attr("abs:src");
                            if(StringUtils.isEmpty(imagesPath)){
                                imagesPath = link.attr("src");
                            }
                            if(!imagesPath.startsWith("http")){
                                String oldPath=imagesPath;
                                imagesPath=baseUrl+imagesPath;
                                content=content.replaceAll(oldPath,imagesPath);
                            }
                            imgUrls.add(imagesPath);
                        }
                        if (CollectionUtils.isNotEmpty(imgUrls)) {

                            String paramsKey=key+DOWNLOAD_CONTENT_IMG_PARAM;
                            String paramsKeyValue=String.valueOf(data.get(paramsKey));
                            String folder=null;
                            if(StringUtils.isNotEmpty(paramsKeyValue)){
                                String[] arr=paramsKeyValue.split("#");
                                if(arr!=null&&arr.length==1){
                                    folder=arr[0];
                                }
                            }

                            //多线程下载图片
                            String newValue = threadDownloadImage(imgUrls,content,folder);
                            //重新赋值
                            resultItems.getAll().put(key,newValue);
                        }
                    }
                }
            }
        }

    }

    public String threadDownloadImage(List<String> imgUrls,String contentValue,String folder){
        ExecutorService service = ThreadUtil.newExecutor(3);
        MultiThreadHandler handler = new ParallelTaskWithThreadPool(service);
        Runnable task = null;
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
        for(String url:imgUrls){
            task=new Runnable() {
                @Override
                public void run() {
                    if (FileUploadUtils.checkUrlExists(url)) {//网络图片存在
                        String path = downloadImage(url,folder);
                        resultMap.put(url,!"error".equals(path)?path:url);
                    }
                }
            };
            handler.addTask(task);
        }
        try {
            handler.run();
        } catch (ChildThreadException e) {
            e.printStackTrace();
        }
        service.shutdown();
        //表里result结果，把下载图片成功的图片替换成本地路径
        for(Map.Entry entry:resultMap.entrySet()){
            String key=String.valueOf(entry.getKey());
            String value=String.valueOf(entry.getValue());
            contentValue=contentValue.replaceAll(key,value);
        }
        return contentValue;
    }
    private static String downloadImage(String url,String folder){
        return  DownloadFileUtil.downloadFileUrlToLocal(url,null,folder,null);
    }
}
