package com.ruoyi.cms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.plugs.common.utils.Guid;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cms.mapper.CmsResourceMapper;
import com.ruoyi.cms.domain.CmsResource;
import com.ruoyi.cms.service.ICmsResourceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 资源Service业务层处理
 *
 * @author wujiyue
 * @date 2019-11-23
 */
@Service
public class CmsResourceServiceImpl implements ICmsResourceService
{
    @Autowired
    private CmsResourceMapper cmsResourceMapper;

    /**
     * 查询资源
     *
     * @param id 资源ID
     * @return 资源
     */
    @Override
    public CmsResource selectResourceById(String id)
    {
        return cmsResourceMapper.selectResourceById(id);
    }

    /**
     * 查询资源列表
     *
     * @param resource 资源
     * @return 资源
     */
    @Override
    public List<CmsResource> selectResourceList(CmsResource resource)
    {
        return cmsResourceMapper.selectResourceList(resource);
    }

    /**
     * 新增资源
     *
     * @param resource 资源
     * @return 结果
     */
    @Override
    public int insertResource(CmsResource resource)
    {
        resource.setId(Guid.get());
        SysUser user= ShiroUtils.getSysUser();
        resource.setUserId(user.getUserId().toString());
        resource.setUserName(user.getUserName());

        resource.setCreateTime(DateUtils.getNowDate());
        return cmsResourceMapper.insertResource(resource);
    }

    /**
     * 修改资源
     *
     * @param resource 资源
     * @return 结果
     */
    @Override
    public int updateResource(CmsResource resource)
    {
        resource.setUpdateTime(DateUtils.getNowDate());
        return cmsResourceMapper.updateResource(resource);
    }

    /**
     * 删除资源对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteResourceByIds(String ids)
    {
        return cmsResourceMapper.deleteResourceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资源信息
     *
     * @param id 资源ID
     * @return 结果
     */
    @Override
    public int deleteResourceById(String id)
    {
        return cmsResourceMapper.deleteResourceById(id);
    }

    @Override
    public int upVote(String id) {
        return cmsResourceMapper.upVote(id);
    }

    @Override
    public int resourceLook(String id) {
        return cmsResourceMapper.resourceLook(id);
    }
}
