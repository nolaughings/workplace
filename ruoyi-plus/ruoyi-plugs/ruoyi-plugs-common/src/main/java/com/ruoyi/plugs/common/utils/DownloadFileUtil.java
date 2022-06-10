package com.ruoyi.plugs.common.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.plugs.common.config.Global;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

public class DownloadFileUtil {

    public static void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(filePath);
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");//只读模式
        long contentLength = randomFile.length();
        String range = request.getHeader("Range");
        int start = 0, end = 0;
        if (range != null && range.startsWith("bytes=")) {
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            if (values.length > 1) {
                end = Integer.parseInt(values[1]);
            }
        }
        int requestSize = 0;
        if (end != 0 && end > start) {
            requestSize = end - start + 1;
        } else {
            requestSize = Integer.MAX_VALUE;
        }
        byte[] buffer = new byte[4096];
        response.setContentType("audio/mpeg");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", filePath);
        response.setHeader("Last-Modified", new Date().toString());
        //第一次请求只返回content length来让客户端请求多次实际数据
        if (range == null) {
            response.setHeader("Content-length", contentLength + "");
        } else {
            //以后的多次以断点续传的方式来返回视频数据
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
            long requestStart = 0, requestEnd = 0;
            String[] ranges = range.split("=");
            if (ranges.length > 1) {
                String[] rangeDatas = ranges[1].split("-");
                requestStart = Integer.parseInt(rangeDatas[0]);
                if (rangeDatas.length > 1) {
                    requestEnd = Integer.parseInt(rangeDatas[1]);
                }
            }
            long length = 0;
            if (requestEnd > 0) {
                length = requestEnd - requestStart + 1;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
            } else {
                length = contentLength - requestStart;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1) + "/" + contentLength);
            }
        }
        ServletOutputStream out = response.getOutputStream();
        int needSize = requestSize;
        randomFile.seek(start);
        while (needSize > 0) {
            int len = randomFile.read(buffer);
            if (needSize < buffer.length) {
                out.write(buffer, 0, needSize);
            } else {
                out.write(buffer, 0, len);
                if (len < buffer.length) {
                    break;
                }
            }
            needSize -= buffer.length;
        }
        randomFile.close();
        out.close();
    }

    /**
     * 下载网络文件到本地
     * @param url 网络文件路径
     * @param baseDir 存放本地文件根路径，可为空
     * @param folder 存放本地文件追加的目录，可为空
     * @param suffix 文件存储类型，可为空
     * @return
     */
    public static String downloadFileUrlToLocal(String url,String baseDir,String folder,String suffix){
        String fileName = extractFilename(url,folder,suffix);
        if(StringUtils.isEmpty(baseDir)){
            baseDir= Global.getUploadPath();
        }
        File file = null;
        try {
            file = getAbsoluteFile(baseDir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            try {
                FileOutputStream fout = new FileOutputStream(file);
                int l = -1;
                byte[] tmp = new byte[1024];
                while ((l = in.read(tmp)) != -1) {
                    fout.write(tmp,0,l);
                }
                fout.flush();
                fout.close();
            } finally {
                in.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return "error";
        }
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }
    public static void downloadFileUrl(String url, OutputStream os){
        String fileName = extractFilename(url,"","");

        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse responseFile = httpclient.execute(httpget);
            HttpEntity entity = responseFile.getEntity();
            InputStream in = entity.getContent();

            byte[] b = new byte[1024];
            int length;
            while ((length = in.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
            in.close();
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {

        }

    }
    private static final String extractFilename(String url,String folder,String suffix)
    {
        if(StringUtils.isEmpty(suffix)){
            suffix=url.substring(url.lastIndexOf("."));
        }else{
            if(!suffix.startsWith(".")){
                suffix="."+suffix;
            }
        }
        String name= UUID.randomUUID().toString()+suffix;
        String folderStr="";
        if("/".equals(folder)){
            folder="";
        }
        if(StringUtils.isNotEmpty(folder)){
            if(folder.startsWith("/")){
                folder=folder.substring(1,folder.length());
            }
            if(folder.endsWith("/")){
                folder=folder.substring(0,folder.length()-1);
            }
            folderStr=folder;
            folderStr+=File.separator;
        }
        String fileName =folderStr+ DateUtils.datePath() + File.separator + name;
        return fileName;
    }
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }
    private static final String getPathFileName(String uploadDir, String fileName)
    {
        if(StringUtils.isNotEmpty(Global.getProfile())){
            int dirLastIndex = Global.getProfile().length() + 1;
            String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
            String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
            return pathFileName;
        }else{
            return uploadDir+File.separator+fileName;
        }

    }
}
