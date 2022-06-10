package com.ruoyi.spider.pipeline;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.plugs.common.exception.BusinessException;
import com.ruoyi.plugs.system.service.ICommonService;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonDbPipeline implements Pipeline {

    private String tableName;
    private ICommonService commonService;
    private List<String> primaryKeys;
    private Boolean hasCreateTime;
    private String createTimeKey="";

    /**
     * 只能通过构造方法new出对象
     * @param tableName
     * @param commonService
     */
    public CommonDbPipeline(String tableName, ICommonService commonService){
        List<String> allTables=commonService.selectAllTables();
        List<String> temp=allTables.stream().filter(a->{return a.equals(tableName);}).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(temp)){
            throw new BusinessException("数据库中不存在表["+tableName+"]!");
        }
        this.tableName=tableName;
        this.commonService=commonService;
        List<String> keys = commonService.selectPrimaryKeys(this.tableName);
        this.primaryKeys=keys;
        List<String> allColumns=commonService.selectAllColumns(tableName);
        if(allColumns!=null&&allColumns.contains("create_time")){
            hasCreateTime=true;
            createTimeKey="create_time";
        }
        if(allColumns!=null&&allColumns.contains("createTime")){
            hasCreateTime=true;
            createTimeKey="createTime";
        }
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> data = resultItems.getAll();
        if(hasCreateTime){
            resultItems.getAll().put(createTimeKey, DateUtils.getTime());
        }
        for(String column:primaryKeys){
            if(data.containsKey(column)){
                boolean auto=commonService.isAutoColumn(tableName,column);
                if(auto){
                    data.remove(column);//自动增长的主键从数据中移除
                }
            }
        }
        commonService.generalInsert(tableName,data);
    }
}
