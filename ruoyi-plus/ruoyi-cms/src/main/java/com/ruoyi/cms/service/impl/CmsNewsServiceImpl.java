package com.ruoyi.cms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cms.mapper.CmsNewsMapper;
import com.ruoyi.cms.domain.CmsNews;
import com.ruoyi.cms.service.ICmsNewsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 新闻Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Service
public class CmsNewsServiceImpl implements ICmsNewsService 
{
    @Autowired
    private CmsNewsMapper cmsNewsMapper;

    /**
     * 查询新闻
     * 
     * @param newsId 新闻主键
     * @return 新闻
     */
    @Override
    public CmsNews selectCmsNewsByNewsId(Long newsId)
    {
        return cmsNewsMapper.selectCmsNewsByNewsId(newsId);
    }

    /**
     * 查询新闻列表
     * 
     * @param cmsNews 新闻
     * @return 新闻
     */
    @Override
    public List<CmsNews> selectCmsNewsList(CmsNews cmsNews)
    {
        return cmsNewsMapper.selectCmsNewsList(cmsNews);
    }

    /**
     * 新增新闻
     * 
     * @param cmsNews 新闻
     * @return 结果
     */
    @Override
    public int insertCmsNews(CmsNews cmsNews)
    {
        return cmsNewsMapper.insertCmsNews(cmsNews);
    }

    /**
     * 修改新闻
     * 
     * @param cmsNews 新闻
     * @return 结果
     */
    @Override
    public int updateCmsNews(CmsNews cmsNews)
    {
        return cmsNewsMapper.updateCmsNews(cmsNews);
    }

    /**
     * 批量删除新闻
     * 
     * @param newsIds 需要删除的新闻主键
     * @return 结果
     */
    @Override
    public int deleteCmsNewsByNewsIds(String newsIds)
    {
        return cmsNewsMapper.deleteCmsNewsByNewsIds(Convert.toStrArray(newsIds));
    }

    /**
     * 删除新闻信息
     * 
     * @param newsId 新闻主键
     * @return 结果
     */
    @Override
    public int deleteCmsNewsByNewsId(Long newsId)
    {
        return cmsNewsMapper.deleteCmsNewsByNewsId(newsId);
    }
}
