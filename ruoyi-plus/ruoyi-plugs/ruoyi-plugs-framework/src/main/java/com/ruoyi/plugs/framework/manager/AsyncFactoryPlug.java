package com.ruoyi.plugs.framework.manager;

import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.plugs.system.domain.CostTime;
import com.ruoyi.plugs.system.service.ICostTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 *
 */
public class AsyncFactoryPlug
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录响应时间
     *
     * @param costTime 消耗时间
     * @return 任务task
     */
    public static TimerTask recordCostTime(final CostTime costTime) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                SpringUtils.getBean(ICostTimeService.class).insertCostTime(costTime);
            }
        };
    }

    /**
     * 清理过期的数据库备份文件
     * @param days  如果传入30，表示清理30天前的文件
     * @param folder 存放备份文件的文件夹路径
     * @return
     */
    public static TimerTask cleanOutDateBackupFile(final Integer days,final String folder) {
        return new TimerTask() {
            @Override
            public void run() {
                File file=new File(folder);
                File[] files =  file.listFiles();
                Date now=new Date();
                Date daysAgo= DateUtils.addDays(now,days>0?-days:days);
                String name="";
                for(File f:files){
                    //通过文件名称判断日期如 ry_2019_10_19_11_34_943.sql
                    name=f.getName();
                    name=name.replace("ry_","");
                    name=name.substring(0,10);
                    name=name.replace("_","-");
                    Date date= DateUtils.parseDate(name);
                    if(date.before(daysAgo)){
                        if(f.exists()){
                            f.delete();
                        }
                    }
                }
            }
        };
    }
}
