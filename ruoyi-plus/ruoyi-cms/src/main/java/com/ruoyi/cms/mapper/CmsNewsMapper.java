package com.ruoyi.cms.mapper;

import java.util.List;
import com.ruoyi.cms.domain.CmsNews;

/**
 * 新闻Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public interface CmsNewsMapper 
{
    /**
     * 查询新闻
     * 
     * @param newsId 新闻主键
     * @return 新闻
     */
    public CmsNews selectCmsNewsByNewsId(Long newsId);

    /**
     * 查询新闻列表
     * 
     * @param cmsNews 新闻
     * @return 新闻集合
     */
    public List<CmsNews> selectCmsNewsList(CmsNews cmsNews);

    /**
     * 新增新闻
     * 
     * @param cmsNews 新闻
     * @return 结果
     */
    public int insertCmsNews(CmsNews cmsNews);

    /**
     * 修改新闻
     * 
     * @param cmsNews 新闻
     * @return 结果
     */
    public int updateCmsNews(CmsNews cmsNews);

    /**
     * 删除新闻
     * 
     * @param newsId 新闻主键
     * @return 结果
     */
    public int deleteCmsNewsByNewsId(Long newsId);

    /**
     * 批量删除新闻
     * 
     * @param newsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmsNewsByNewsIds(String[] newsIds);
}
