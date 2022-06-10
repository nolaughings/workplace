package com.ruoyi.cms.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻对象 cms_news
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public class CmsNews extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 新闻编号 */
    @Excel(name = "新闻编号")
    private Long newsId;

    /** 新闻照片 */
    @Excel(name = "新闻照片")
    private String newsImg;

    /** 新闻标题 */
    @Excel(name = "新闻标题")
    private String newsTitle;

    /** 新闻内容 */
    @Excel(name = "新闻内容")
    private String newsContent;

    /** 新闻发布状态 */
    @Excel(name = "新闻发布状态")
    private Integer newsPublish;

    /** 新闻发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新闻发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date newsPublishdate;

    public void setNewsId(Long newsId) 
    {
        this.newsId = newsId;
    }

    public Long getNewsId() 
    {
        return newsId;
    }
    public void setNewsImg(String newsImg) 
    {
        this.newsImg = newsImg;
    }

    public String getNewsImg() 
    {
        return newsImg;
    }
    public void setNewsTitle(String newsTitle) 
    {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() 
    {
        return newsTitle;
    }
    public void setNewsContent(String newsContent) 
    {
        this.newsContent = newsContent;
    }

    public String getNewsContent() 
    {
        return newsContent;
    }
    public void setNewsPublish(Integer newsPublish) 
    {
        this.newsPublish = newsPublish;
    }

    public Integer getNewsPublish() 
    {
        return newsPublish;
    }
    public void setNewsPublishdate(Date newsPublishdate) 
    {
        this.newsPublishdate = newsPublishdate;
    }

    public Date getNewsPublishdate() 
    {
        return newsPublishdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("newsId", getNewsId())
            .append("newsImg", getNewsImg())
            .append("newsTitle", getNewsTitle())
            .append("newsContent", getNewsContent())
            .append("newsPublish", getNewsPublish())
            .append("newsPublishdate", getNewsPublishdate())
            .toString();
    }
}
