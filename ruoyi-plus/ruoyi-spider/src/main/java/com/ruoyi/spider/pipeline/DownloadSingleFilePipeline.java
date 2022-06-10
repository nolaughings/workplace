package com.ruoyi.spider.pipeline;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.common.utils.DownloadFileUtil;
import com.ruoyi.plugs.common.utils.FileUploadUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import java.util.List;
import java.util.Map;

public class DownloadSingleFilePipeline implements Pipeline {

    public static final String DOWNLOAD_SINGLE_FILE="_downloadSingleFile";
    public static final String DOWNLOAD_SINGLE_FILE_PARAM="_downloadSingleFileParam";
    /**
     * 把字段是网络文件的下载到本地(只下载单个字段是文件的，而例如文章内容有网络图片的情况不做下载处理)
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> map = resultItems.getAll();
        String isList=(String)map.get("isList");
        if("1".equals(isList)){//是集合
            List<Map<String, Object>> list =(List<Map<String, Object>>) map.get("list");
            for(Map<String, Object> data:list){
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String key=entry.getKey();
                    String checkKey=key+DOWNLOAD_SINGLE_FILE;
                    String checkKeyValue=String.valueOf(map.get(checkKey));
                    if("1".equals(checkKeyValue)){
                        //需要下载
                        String value=String.valueOf(entry.getValue());
                        if(FileUploadUtils.checkUrlExists(value)){//网络图片存在
                            String paramsKey=key+DOWNLOAD_SINGLE_FILE_PARAM;
                            String paramsKeyValue=String.valueOf(map.get(paramsKey));
                            String folder=null;
                            String suffix=null;
                            if(StringUtils.isNotEmpty(paramsKeyValue)){
                                String[] arr=paramsKeyValue.split("#");
                                if(arr!=null&&arr.length==2){
                                    folder=arr[0];
                                    suffix=arr[1];
                                }else if(arr!=null&&arr.length==1){
                                    if(paramsKeyValue.startsWith("#")){
                                        suffix=arr[0];
                                    }else{
                                        folder=arr[0];
                                    }
                                }
                            }
                            String path = downloadFile(value,folder,suffix);
                            data.put(key,path.equals("error")?value:path);//重新赋值
                        }
                    }
                }
            }
            resultItems.getAll().put("list",list);
        }else{
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key=entry.getKey();
                String checkKey=key+DOWNLOAD_SINGLE_FILE;
                String checkKeyValue=String.valueOf(map.get(checkKey));
                if("1".equals(checkKeyValue)){
                    //需要下载
                    String value=String.valueOf(entry.getValue());
                    if(FileUploadUtils.checkUrlExists(value)){//网络图片存在
                        String paramsKey=key+DOWNLOAD_SINGLE_FILE_PARAM;
                        String paramsKeyValue=String.valueOf(map.get(paramsKey));
                        String baseDir=null;
                        String folder=null;
                        String suffix=null;
                        if(StringUtils.isNotEmpty(paramsKeyValue)){
                            String[] arr=paramsKeyValue.split("#");
                            if(arr!=null&&arr.length==2){
                                folder=arr[0];
                                suffix=arr[1];
                            }else if(arr!=null&&arr.length==1){
                                if(paramsKeyValue.startsWith("#")){
                                    suffix=arr[0];
                                }else{
                                    folder=arr[0];
                                }
                            }
                        }
                        String path = downloadFile(value,folder,suffix);
                        resultItems.getAll().put(key,path.equals("error")?value:path);//重新赋值
                    }
                }
            }
        }
    }

    public static String downloadFile(String url,String folder,String suffix){
        return  DownloadFileUtil.downloadFileUrlToLocal(url,null,folder,suffix);
    }
}
