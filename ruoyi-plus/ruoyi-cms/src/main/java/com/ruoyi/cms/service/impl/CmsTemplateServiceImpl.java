package com.ruoyi.cms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cms.mapper.CmsTemplateMapper;
import com.ruoyi.cms.domain.CmsTemplate;
import com.ruoyi.cms.service.ICmsTemplateService;
import com.ruoyi.common.core.text.Convert;

/**
 * 模板Service业务层处理
 *
 * @author wujiyue
 * @date 2019-11-17
 */
@Service
public class CmsTemplateServiceImpl implements ICmsTemplateService
{
    @Autowired
    private CmsTemplateMapper cmsTemplateMapper;

    /**
     * 查询模板
     *
     * @param templateId 模板ID
     * @return 模板
     */
    @Override
    public CmsTemplate selectTemplateById(Long templateId)
    {
        return cmsTemplateMapper.selectTemplateById(templateId);
    }

    @Override
    public CmsTemplate selectTemplateByCode(String templateCode) {
        return cmsTemplateMapper.selectTemplateByCode(templateCode);
    }

    /**
     * 查询模板列表
     *
     * @param template 模板
     * @return 模板
     */
    @Override
    public List<CmsTemplate> selectTemplateList(CmsTemplate template)
    {
        return cmsTemplateMapper.selectTemplateList(template);
    }

    /**
     * 新增模板
     *
     * @param template 模板
     * @return 结果
     */
    @Override
    public int insertTemplate(CmsTemplate template)
    {
        return cmsTemplateMapper.insertTemplate(template);
    }

    /**
     * 修改模板
     *
     * @param template 模板
     * @return 结果
     */
    @Override
    public int updateTemplate(CmsTemplate template)
    {
        return cmsTemplateMapper.updateTemplate(template);
    }

    /**
     * 删除模板对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTemplateByIds(String ids)
    {
        return cmsTemplateMapper.deleteTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模板信息
     *
     * @param templateId 模板ID
     * @return 结果
     */
    @Override
    public int deleteTemplateById(Long templateId)
    {
        return cmsTemplateMapper.deleteTemplateById(templateId);
    }
}
