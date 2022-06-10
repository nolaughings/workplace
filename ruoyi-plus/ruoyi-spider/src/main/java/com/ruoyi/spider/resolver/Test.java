package com.ruoyi.spider.resolver;

import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.plugs.common.utils.Md5Utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        /*String str="sdfs@dfd";
        String rex="[#|@|*]";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);
        boolean exists=matcher.find();
        System.out.println(exists);*/
        /*String s="1.8 <a href=\"/view/vip_7044.html\">Java是如何实现跨平台的，原理是什么？</a><span class=\"glyphicon glyphicon-usd\"></span>";

        s=s.replaceAll("", "");
        System.out.println(s);*/
        String s="C:\\Users\\wjy\\Desktop\\bq3a1djipt34q3epdqcg.mp3";
        String md5= Md5Utils.hash(new File(s));
        System.out.println(md5);

    }
}
