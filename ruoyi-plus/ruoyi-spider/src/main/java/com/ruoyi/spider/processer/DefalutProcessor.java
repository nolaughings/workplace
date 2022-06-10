package com.ruoyi.spider.processer;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.plugs.common.exception.BusinessException;
import com.ruoyi.plugs.system.service.ICommonService;
import com.ruoyi.spider.MyConfigurableSpider;
import com.ruoyi.spider.domain.SpiderConfig;
import com.ruoyi.spider.domain.SpiderField;
import com.ruoyi.spider.downloader.HttpClientDownloader;
import com.ruoyi.spider.downloader.SeleniumDownloader;
import com.ruoyi.spider.pipeline.DownloadContentImagePipeline;
import com.ruoyi.spider.pipeline.DownloadSingleFilePipeline;
import com.ruoyi.spider.pipeline.CommonDbPipeline;
import com.ruoyi.spider.pipeline.SelectCoverImagePipeline;
import com.ruoyi.spider.scheduler.CountDownScheduler;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 爬虫入口
 */
public class DefalutProcessor extends AbstractProcessor {


    private ISysConfigService configService= SpringUtils.getBean(ISysConfigService.class);
    private ICommonService generalService= SpringUtils.getBean(ICommonService.class);

    public DefalutProcessor(SpiderConfig config) {
        super(config);
    }

    public DefalutProcessor(SpiderConfig config, String uuid) {
        super(config, uuid);
    }

    /**
     * 运行爬虫并返回结果
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<LinkedHashMap<String, Object>> execute() {
        List<String> errors = this.validateModel(config);
        if (CollectionUtils.isNotEmpty(errors)) {
            logger.warn("校验不通过！请依据下方提示，检查输入参数是否正确......");
            for (String error : errors) {
                logger.warn(">> " + error);
            }
            return null;
        }
        List<SpiderField> fields = config.getFieldsList();
        if(CollectionUtils.isEmpty(fields)&&config.getIsJson()!=1){
            logger.warn("校验不通过！爬虫字段对应规则未配置!!!");
            return null;
        }
        //设置用户自定义pipeline
        if(StringUtils.isNotEmpty(config.getUserDefinePipeline())){
            String[] arr=config.getUserDefinePipeline().split(",");
            if(arr!=null&&arr.length>0){
                for(String str:arr){
                    if(StringUtils.isNotEmpty(str)){
                        char[]chars = str.toCharArray();
                        chars[0] += 32;
                        str=String.valueOf(chars);
                        Pipeline pipeline = SpringUtils.getBean(str);
                       if(pipeline!=null){
                           config.addPipeline(pipeline);
                       }
                    }
                }
            }
        }
        CopyOnWriteArrayList<LinkedHashMap<String, Object>> datas = new CopyOnWriteArrayList<>();
        MyConfigurableSpider spider = MyConfigurableSpider.create(this, config, uuid);
        spider.addUrl(config.getEntryUrlsList().toArray(new String[0]));

               if(config.getIsSelenium()==1) {
                   String path=configService.selectConfigByKey("webdriver.chrome.driver");
                   if(StringUtils.isEmpty(path)){
                       throw new BusinessException("请在sys_config表配置webdriver.chrome.driver的值!");
                   }
                   if(!new File(path).exists()){
                       throw new BusinessException("webdriver.chrome.driver路径对应的文件不存在!");
                   }
                   spider.setDownloader(new SeleniumDownloader(path,config));
               }else{
                   spider.setDownloader(new HttpClientDownloader());
               }

                spider.setScheduler(new CountDownScheduler(config))
                .thread(config.getThreadCount().intValue());


        //设置抓取代理IP
        if (config.getUseProxy()==1 && !CollectionUtils.isEmpty(config.getProxyList())) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            SimpleProxyProvider provider = SimpleProxyProvider.from(config.getProxyList().toArray(new Proxy[0]));
            httpClientDownloader.setProxyProvider(provider);
            spider.setDownloader(httpClientDownloader);
        }
        //内置Pipeline 设置1:DownloadSingleFilePipeline  2:DownloadContentImagePipeline  3:SelectCoverImagePipeline

        List<String> spiderFieldHighSettingList=new ArrayList<>();
        for(SpiderField field:fields){
            if(StringUtils.isNotEmpty(field.getSpiderFieldHighSetting())){
                String[] arr= Convert.toStrArray(field.getSpiderFieldHighSetting());
                List<String> list=Arrays.asList(arr);
                spiderFieldHighSettingList.addAll(list);
            }
        }

        if(spiderFieldHighSettingList.contains("1")){
            //下载文件到本地
            spider.addPipeline(new DownloadSingleFilePipeline());
        }
        if(spiderFieldHighSettingList.contains("2")){
            //下载内容详情里的图片到本地
            spider.addPipeline(new DownloadContentImagePipeline());
        }
        if(spiderFieldHighSettingList.contains("3")){
            //选取内容详情图片的第一张作为封面图片
            spider.addPipeline(new SelectCoverImagePipeline());
        }

        //spider.setPipelines(config.getPipelineList());//设置自定义的Pipeline,这种方式设置会覆盖掉上面的，应一个个添加
        if(CollectionUtils.isNotEmpty(config.getPipelineList())){
            for(Pipeline pipeline:config.getPipelineList()){
                spider.addPipeline(pipeline);
            }
        }
        //控制台输出管道
        if(config.getShowLog()==1){
            spider.addPipeline(new ConsolePipeline());
        }
        //默认的数据库输出管道
        if(config.getSaveDb()==1){
            spider.addPipeline(new CommonDbPipeline(config.getTableName(),generalService));
        }
        spider.addPipeline((resultItems, task) -> this.processData(resultItems, datas, spider)); // 收集数据
        // 启动爬虫
        spider.run();
        return datas;
    }


}
