package com.ruoyi.plugs.system.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.system.domain.UserInvite;
import com.ruoyi.plugs.system.mapper.CommonMapper;
import com.ruoyi.plugs.system.service.ICommonService;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonService implements ICommonService {

    @Autowired
    private SysConfigMapper configMapper;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public int updateDicValueByKey(String key, String configValue) {
        SysConfig info = configMapper.checkConfigKeyUnique(key);
        if (StringUtils.isNotNull(info))
        {
            info.setConfigValue(configValue);
            return sysConfigService.updateConfig(info);
        }
        return 0;
    }

    @Override
    public int insertUserInvite(UserInvite userInvite) {
        return commonMapper.insertUserInvite(userInvite);
    }

    @Override
    public List<UserInvite> selectUserInvite(String inviteId) {
        return commonMapper.selectUserInvite(inviteId);
    }

    @Override
    public int selectUserInviteCount(String inviteId) {
        return commonMapper.selectUserInviteCount(inviteId);
    }



    @Override
    public void generalInsert(String tableName, Map<String, Object> data) {
        commonMapper.generalInsert(tableName,data);
    }

    @Override
    public List<String> selectPrimaryKeys(String tableName) {
        return commonMapper.selectPrimaryKeys(tableName);
    }

    @Override
    public List<String> selectAllColumns(String tableName) {
        return commonMapper.selectAllColumns(tableName);
    }

    @Override
    public List<String> selectAllTables() {
        return commonMapper.selectAllTables();
    }

    @Override
    public boolean isAutoColumn(String tableName, String columnName) {
        List<String> list=commonMapper.selectColumnExtraInfo(tableName,columnName);
        if(CollectionUtils.isNotEmpty(list)){
            String extra=list.get(0);
            if("auto_increment".equals(extra)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map> selectByMap(String tableName, Map<String, Object> data) {
        return commonMapper.selectByMap(tableName,data);
    }

    @Override
    public List<Map> selectDonateMap(String tableName, Map<String, Object> data) {
        return commonMapper.selectDonateMap(tableName,data);
    }
}
