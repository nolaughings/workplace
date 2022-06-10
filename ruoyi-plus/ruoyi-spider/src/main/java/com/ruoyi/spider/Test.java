package com.ruoyi.spider;


import com.ruoyi.plugs.common.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
       /* String url="https://img95.699pic.com/music_sound_effect/160/608/5d677668df253.mp3";
        String baseDir= Global.getAudioPath();
        String path=DownloadFileUtil.downloadFileUrlToLocal(url,"D:\\app");
        System.out.println(path);

        String s="https://www.zyfx8.cn/wp-content/themes/ripro/timthumb.php?src=https://www.zyfx8.cn/wp-content/uploads/2021/04/28037b6e2675bbbfa7c7fdbf2bbb0f7d.png&h=350&w=350&zc=1&a=c&q=100&s=1";
        System.out.println(StringUtils.trim_mid_exclu(s,"src=","&"));*/
      /*String s1=  Md5Utils.hash(new File("d:\\2.jpg"));
      String s2=  Md5Utils.hash(new File("d:\\6.jpg"));
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));*/
        List<String> allUrl=new ArrayList<String>();
        String s="http://localhost:8099/sys/spider?code=&page=11";
        //   s="https://699pic.com/music/so-so-0-0-0-0-0-complex-0-1-0-36.html";
        //   allUrl=pageUrls_Reg(s,"https://699pic.com/music/so-so-0-0-0-0-0-complex-0-#-0-36.html",3);
         /* allUrl=pageUrls_Reg(s,"http://localhost:8099/sys/spider?code=&page=",3);
        for(int i=0;i<allUrl.size();i++){
            System.out.println(allUrl.get(i));
        }*/
//http://www.lxh5068.com/tapi/acgurl.php
       /* String url="http://www.lxh5068.com/tapi/acgurl.php";
        String baseDir= Global.getAudioPath();
        String path=DownloadFileUtil.downloadFileUrlToLocal(url,"D:\\app","jpg");
        System.out.println(path);*/


       /* String url="https://img95.699pic.com/music_sound_effect/160/608/5d677668df253.mp3";
        String baseDir= Global.getAudioPath();
        String path=DownloadFileUtil.downloadFileUrlToLocal(url,"C:\\Users\\wjy\\Desktop","/audio/effect/",null);
        System.out.println(path);*/
    }

    private static List<String> pageUrls_Reg(String url, String s, int loopNum){
        //根据正则解析分页url
        // http://localhost:8099/sys/spider?code=&page=11
        // https://699pic.com/music/so-so-0-0-0-0-0-complex-0-1-0-36.html
        String[] arr=s.split("#");
        String s1="";
        String s2="";
        if(arr.length==1){
            s1=arr[0];
        }else{
            s1=arr[0];
            s2=arr[1];
        }
        List<String> urls=new ArrayList<String>();
        String  nowNumStr="";
        if(StringUtils.isEmpty(s2)){
            nowNumStr=StringUtils.trim_end_exclu(url,s1);
        }else{
            nowNumStr=StringUtils.trim_mid_exclu(url,s1,s2);
        }
        int nowNum=1;
        nowNum=Integer.valueOf(nowNumStr);
        for(int i=0;i<loopNum;i++){
            url=s1+nowNum+s2;
            urls.add(url);
            nowNum++;
        }
        return urls;
    }
}
