package com.ruoyi.spider.resolver;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.plugs.common.utils.StringUtils;
import com.ruoyi.spider.config.SpiderConstants;
import com.ruoyi.spider.domain.SpiderConfig;
import com.ruoyi.spider.domain.SpiderField;
import com.ruoyi.spider.domain.SpiderFiledRule;
import com.ruoyi.spider.pipeline.DownloadContentImagePipeline;
import com.ruoyi.spider.pipeline.DownloadSingleFilePipeline;
import com.ruoyi.spider.pipeline.SelectCoverImagePipeline;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selector;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析处理普通的Html网页
 */
public class DefaultResolver implements Resolver {

    @Override
    public void process(Page page, SpiderConfig spiderConfig) {
        List<SpiderField> fields = spiderConfig.getFieldsList();

        Html pageHtml = page.getHtml();
        Document pageDoc =page.getHtml().getDocument();
        String link = page.getRequest().getUrl();

        if(!spiderConfig.getEntryUrlsList().contains(link) || Pattern.matches(spiderConfig.getTargetRegex(), link)||link.equals(spiderConfig.getTargetRegex())) {
            //入口页面无需取值，入口页面只是用于发现目标url，除非入口页面匹配目标url的正则
            if(StringUtils.isEmpty(spiderConfig.getListExtractBy())){ //单页内容
                for (SpiderField field : fields) {
                    String column=field.getField();//有可能取得同一个字段需要多个不同规则
                    //先判断该字段是否有值，如果没有在继续处理，有了就不用处理了
                    String checkVal = String.valueOf(page.getResultItems().getAll().get(column));
                    if(StringUtils.isNotEmpty(checkVal)){
                        continue;
                    }
                    String type = field.getExtractType();
                    if (StringUtils.isEmpty(type)||type.equals(SpiderConstants.FIELD_EXTRACT_TYPE_XPATH)) {
                        this.xpath_put(page, pageHtml, field);
                    } else if (type.equals(SpiderConstants.FIELD_EXTRACT_TYPE_CSS)) {


                            this.css_put(page, pageDoc, field);


                    } else if (type.equals(SpiderConstants.FIELD_EXTRACT_TYPE_CONSTANT)) {
                        this.constant_put(page,field);
                    } else {
                    }
                }
            }else{//集合列表
                List<Selectable> selectableList=page.getHtml().css(spiderConfig.getListExtractBy()).nodes();
                this.css_put_list(page, selectableList, fields);
            }
            //page.putField("link", link);
            //设置内置pipeline参数
            for (SpiderField field : fields) {
                if(StringUtils.isNotEmpty(field.getSpiderFieldHighSetting())){
                    String[] arr= Convert.toStrArray(field.getSpiderFieldHighSetting());
                    List<String> spiderFieldHighSettingList=Arrays.asList(arr);
                    if(spiderFieldHighSettingList.contains("1")){//下载文件到本地
                        page.putField(field.getField()+DownloadSingleFilePipeline.DOWNLOAD_SINGLE_FILE,"1");
                        //params=folder + suffix
                        page.putField(field.getField()+DownloadSingleFilePipeline.DOWNLOAD_SINGLE_FILE_PARAM,field.getSpiderFieldHighSettingParams());
                    }
                    if(spiderFieldHighSettingList.contains("2")){//下载内容详情里的图片到本地
                        page.putField(field.getField()+DownloadContentImagePipeline.DOWNLOAD_CONTENT_IMG_FILE,"1");
                        //params=folder
                        page.putField(field.getField()+DownloadContentImagePipeline.DOWNLOAD_CONTENT_IMG_PARAM,field.getSpiderFieldHighSettingParams());
                    }
                    if(spiderFieldHighSettingList.contains("3")){//选取内容详情图片的第一张作为封面图片
                        page.putField(field.getField()+SelectCoverImagePipeline.SELECT_COVER_IMG,"1");
                        //params= folder + contentFieldKey
                        page.putField(field.getField()+SelectCoverImagePipeline.SELECT_COVER_IMG_PARAM,field.getSpiderFieldHighSettingParams());
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(spiderConfig.getTargetRegex())) {
            if(spiderConfig.getCascade()==1 ){//|| spiderConfig.getEntryUrlsList().contains(link)
                //级联发现或者是入口URL才收集目标URL
                page.addTargetRequests(page.getHtml().links().regex(spiderConfig.getTargetRegex()).all());
            }
        }
    }

    /**
     * 处理爬取后原始的文本内容，比如替换截取等操作最终得到自己想要的字符串
     * @param sourceValue
     * @param field
     * @return
     */
    private String processValue(String sourceValue,SpiderField field){
        if(StringUtils.isEmpty(sourceValue) || CollectionUtils.isEmpty(field.getFieldRules())){
            return sourceValue;
        }
        List<SpiderFiledRule> rules = field.getFieldRules();
        rules=rules.stream().sorted(Comparator.comparing(SpiderFiledRule::getSort)).collect(Collectors.toList());
        for(SpiderFiledRule rule:rules){
            if(SpiderConstants.FIELD_PROCESS_TYPE_REPLACE.equals(rule.getProcessType())){
                sourceValue=sourceValue.replaceAll(rule.getReplacereg(),StringUtils.isEmpty(rule.getReplacement())?"":rule.getReplacement());
            }else if(SpiderConstants.FIELD_PROCESS_TYPE_SUBSTRING_AFTER.equals(rule.getProcessType())){
                sourceValue=StringUtils.trim_end_exclu(sourceValue, rule.getSubstrTarget());
            }else if(SpiderConstants.FIELD_PROCESS_TYPE_SUBSTRING_BEFORE.equals(rule.getProcessType())){
                sourceValue=StringUtils.trim_before_exclu(sourceValue, rule.getSubstrTarget());
            }else if(SpiderConstants.FIELD_PROCESS_TYPE_REPLACE_HTML.equals(rule.getProcessType())){
                Pattern p_html=Pattern.compile(SpiderConstants.REGEX_HTML,Pattern.CASE_INSENSITIVE);
                Matcher matcher=p_html.matcher(sourceValue);
                sourceValue=matcher.replaceAll("");
            }else if(SpiderConstants.FIELD_PROCESS_TYPE_REPLACE_A.equals(rule.getProcessType())){
                sourceValue=sourceValue.replaceAll("<a[^<]*?>","").replaceAll("</a>","");
            }else if(SpiderConstants.FIELD_PROCESS_TYPE_MIDDLE.equals(rule.getProcessType())){
                String[] arr=rule.getSubstrTarget().split("#");
                if(arr!=null&&arr.length==2){
                    sourceValue=StringUtils.trim_mid_exclu(sourceValue, arr[0],arr[1]);
                }
            }else{}
        }
        return sourceValue;
    }
    private void xpath_put(Page page, Html pageHtml,SpiderField field) {
        if (StringUtils.isNotEmpty(field.getExtractBy())) {
            String value=pageHtml.xpath(field.getExtractBy()).get();
            value= processValue(value,field);//处理替换
            page.putField(field.getField(), value);
        }
    }
    private void css_put(Page page,Document pageDoc,SpiderField field) {
        String extractIndex=field.getExtractIndex();
        String[] indexArr=null;
        if(!"all".equals(extractIndex)){
            indexArr=extractIndex.split(",");
        }else{ //提取全部
            int size=pageDoc.select(field.getExtractBy()).size();
            indexArr=new String[size];
            for(Integer i=0;i<size;i++){
                indexArr[i]=i+"";
            }
        }
        boolean extractBy2Flag=false;
        if(StringUtils.isNotEmpty(field.getExtractBy2())){
            extractBy2Flag=true;
        }
        String resStr="";
        Elements elements=pageDoc.select(field.getExtractBy());
        if(!"1".equals(field.getExtractAttrFlag())){//非根据属性名取值
            for(String ix:indexArr){
                if(StringUtils.isNotEmpty(ix)){
                    String tempRes="";
                    try{
                        if(!extractBy2Flag){
                            tempRes=  elements.get(Integer.valueOf(ix)).html();
                        }else{
                            tempRes=  elements.get(Integer.valueOf(ix)).select(field.getExtractBy2()).get(0).html();
                        }

                    }catch (Exception ex){}
                    if(StringUtils.isNotEmpty(tempRes)){
                        resStr +=tempRes;
                        resStr +=",";
                    }
                }
            }
        }else{//根据属性名取值
            for(String ix:indexArr) {
                if (StringUtils.isNotEmpty(ix)) {
                    String tempRes="";
                    try {
                        if(!extractBy2Flag) {
                            tempRes= getAttributeByElement(elements.get(Integer.valueOf(ix)),field.getExtractAttr());
                        }else{
                            tempRes= getAttributeByElement(elements.get(Integer.valueOf(ix)).select(field.getExtractBy2()).get(0),field.getExtractAttr());
                        }
                    }catch (Exception ex){}
                    if(StringUtils.isNotEmpty(tempRes)){
                        resStr +=tempRes;
                        resStr +=",";
                    }
                }
            }
        }
        if(resStr.endsWith(",")){
            resStr=resStr.substring(0,resStr.length()-1);
        }
        //处理替换
        resStr= processValue(resStr,field);
        page.putField(field.getField(),resStr);

    }

    private void css_put_list(Page page,List<Selectable> selectableList,List<SpiderField> fields) {
        List<Map<String, Object>> dataList=new ArrayList<>();
        Map tempMap=null;
        for(Selectable selectable:selectableList){
           Document pageDoc =Jsoup.parse(selectable.get());
            tempMap=new HashMap();

            for(SpiderField field:fields){
                String extractIndex=field.getExtractIndex();
                String[] indexArr=null;
                if(!"all".equals(extractIndex)){
                    indexArr=extractIndex.split(",");
                }else{ //提取全部
                    int size=pageDoc.select(field.getExtractBy()).size();
                    indexArr=new String[size];
                    for(Integer i=0;i<size;i++){
                        indexArr[i]=i+"";
                    }
                }
                boolean extractBy2Flag=false;
                if(StringUtils.isNotEmpty(field.getExtractBy2())){
                    extractBy2Flag=true;
                }
                String resStr="";
                Elements elements=pageDoc.select(field.getExtractBy());
                if(!"1".equals(field.getExtractAttrFlag())){//非根据属性名取值
                    for(String ix:indexArr){
                        if(StringUtils.isNotEmpty(ix)){
                            String tempRes="";
                            try{
                                if(!extractBy2Flag){
                                    tempRes=  elements.get(Integer.valueOf(ix)).html();
                                }else{
                                    tempRes=  elements.get(Integer.valueOf(ix)).select(field.getExtractBy2()).get(0).html();
                                }

                            }catch (Exception ex){}
                            if(StringUtils.isNotEmpty(tempRes)){
                                resStr +=tempRes;
                                resStr +=",";
                            }
                        }
                    }
                }else{//根据属性名取值
                    for(String ix:indexArr) {
                        if (StringUtils.isNotEmpty(ix)) {
                            String tempRes="";
                            try {
                                if(!extractBy2Flag) {
                                    tempRes= getAttributeByElement(elements.get(Integer.valueOf(ix)),field.getExtractAttr());
                                }else{
                                    tempRes= getAttributeByElement(elements.get(Integer.valueOf(ix)).select(field.getExtractBy2()).get(0),field.getExtractAttr());
                                }
                            }catch (Exception ex){}
                            if(StringUtils.isNotEmpty(tempRes)){
                                resStr +=tempRes;
                                resStr +=",";
                            }
                        }
                    }
                }
                if(resStr.endsWith(",")){
                    resStr=resStr.substring(0,resStr.length()-1);
                }
                //处理替换
                resStr= processValue(resStr,field);
                //page.putField(field.getField(),resStr);
                tempMap.put(field.getField(),resStr);
            }

            dataList.add(tempMap);
        }

        page.putField("list",dataList);
        page.putField("isList","1");
    }

    private void constant_put(Page page,SpiderField field) {
        page.putField(field.getField(),field.getConstantValue());
    }

    private String getAttributeByElement(Element e, String attrName){
        // 判断如果属性名是href或者src
        String res = "";
        if ("href".equals(attrName) || "src".equals(attrName)) {
            // 因为要获取他们绝对路径
            res = e.attr("abs:" + attrName);
            if(StringUtils.isEmpty(res)){
                res = e.attr(attrName);
            }
        } else {
            //不是href或者src
            res = e.attr(attrName);
        }
        return res;
    }
}
