package com.ruoyi.plugs.system.service;

import com.ruoyi.plugs.system.domain.UserInvite;

import java.util.List;
import java.util.Map;

public interface ICommonService {
    /**
     * 更新字典值
     * @param key
     * @param configValue
     * @author markbro
     */
    public int updateDicValueByKey(String key, String configValue);

    /**
     * 保存用户邀请信息
     */
    public int insertUserInvite(UserInvite userInvite);
    /**
     * 查询某个用户的用户邀请信息列表
     */
    public List<UserInvite> selectUserInvite(String inviteId);

    public int selectUserInviteCount(String inviteId);

    public void generalInsert(String tableName, Map<String, Object> data);
    List<String> selectPrimaryKeys(String tableName);
    List<String> selectAllColumns(String tableName);
    List<String> selectAllTables();
    boolean isAutoColumn(String tableName,String columnName);
    List<Map> selectByMap(String tableName,Map<String, Object> data);
    List<Map> selectDonateMap(String tableName,Map<String, Object> data);
}
