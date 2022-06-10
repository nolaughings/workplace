package com.ruoyi.spider.pipeline;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.common.utils.DownloadFileUtil;
import com.ruoyi.plugs.common.utils.FileUploadUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;
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

/**
 * 抽取内容详情的第一张图片作为封面图片
 */
public class SelectCoverImagePipeline implements Pipeline {

    private static final String CONTENT_FIELD="content";

    public static final String SELECT_COVER_IMG="_selectCoverImg";
    public static final String SELECT_COVER_IMG_PARAM="_selectCoverImgParam";
    /**
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> data = resultItems.getAll();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String coverImgKey = entry.getKey();
            String checkKey = coverImgKey + SELECT_COVER_IMG;
            String checkKeyValue = String.valueOf(data.get(checkKey));
            if ("1".equals(checkKeyValue)) {
                String coverImgValue=String.valueOf(data.get(coverImgKey));
                if(StringUtils.isEmpty(coverImgValue)){
                    String paramsKey=coverImgKey+SELECT_COVER_IMG_PARAM;
                    String paramsKeyValue=String.valueOf(data.get(paramsKey));
                    String folder=null;
                    String contentFieldKey=null;//用来找到内容详情字段，如果没设置用默认的content
                    if(StringUtils.isNotEmpty(paramsKeyValue)){
                        String[] arr=paramsKeyValue.split("#");
                        if(arr!=null&&arr.length==2){
                            folder=arr[0];
                            contentFieldKey=arr[1];
                        }else if(arr!=null&&arr.length==1){
                            if(paramsKeyValue.startsWith("#")){
                                contentFieldKey=arr[0];
                            }else{
                                folder=arr[0];
                            }
                        }
                    }
                    if(StringUtils.isEmpty(contentFieldKey)){
                        contentFieldKey=CONTENT_FIELD;
                    }
                    String contentValue = String.valueOf(data.get(contentFieldKey));
                    if(StringUtils.isNotEmpty(contentValue)){
                        Document doc = Jsoup.parse(contentValue);
                        if (doc != null) {
                            List<String> imgUrls = new ArrayList<String>();
                            Elements elements = doc.select("img");
                            if (elements != null && elements.size() > 0) {
                                Element first = elements.first();
                                coverImgValue = first.attr("src");
                                /*if(StringUtils.isEmpty(coverImgValue)){
                                    coverImgValue=first.attr("src");
                                }*/
                            }
                        }
                    }

                    if(!coverImgValue.startsWith("http")){//此时在内容中已经下载了该图片
                        resultItems.getAll().put(coverImgKey,coverImgValue);
                        return;
                    }
                    if(StringUtils.isNotEmpty(coverImgValue)&& FileUploadUtils.checkUrlExists(coverImgValue)){
                        String newPath=downloadImage(coverImgValue,folder);
                        resultItems.getAll().put(coverImgKey,newPath.equals("error")?coverImgValue:newPath);
                    }
                }
            }
        }
    }

    private static String downloadImage(String url,String folder){
        return  DownloadFileUtil.downloadFileUrlToLocal(url,null,folder,null);
    }
}
