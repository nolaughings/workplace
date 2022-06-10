package com.ruoyi.plugs.system.mapper;


import com.ruoyi.plugs.system.domain.UserInvite;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CommonMapper
{
    /**
     * 保存用户邀请信息
     */
    public int insertUserInvite(UserInvite userInvite);

    /**
     * 查询某个用户的用户邀请信息列表
     */
    public List<UserInvite> selectUserInvite(String inviteId);

    public int selectUserInviteCount(String inviteId);

    void generalInsert(@Param("tableName") String tableName, @Param("data") Map<String, Object> data);
    List<String> selectPrimaryKeys(@Param("tableName") String tableName);
    List<String> selectAllColumns(@Param("tableName") String tableName);
    List<String> selectAllTables();
    List<String> selectColumnExtraInfo(@Param("tableName") String tableName,@Param("columnName") String columnName);
    List<Map> selectByMap(@Param("tableName") String tableName,@Param("data") Map<String, Object> data);
    List<Map> selectDonateMap(@Param("tableName") String tableName,@Param("data") Map<String, Object> data);
    public int selectTableExists(@Param("dbName") String dbName,@Param("tableName") String tableName);
}
