package com.ruoyi.plugs.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 系统模块对象 plugs_sys_module
 *
 * @author ruoyi
 * @date 2021-08-27
 */
public class SysModule extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 代码" */
    @Excel(name = "代码")
    private String code;

    /** 名称 */
    @Excel(name = "名称")
    private String name;
    /** 版本号 */
    @Excel(name = "版本号")
    private String version;
    /** 模块状态 */
    @Excel(name = "模块状态")
    private Integer moduleState;

    /** 类型 */
    @Excel(name = "类型")
    private String moduleType;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 封面图片 */
    @Excel(name = "封面图片")
    private String coverImg;

    /** 截图 */
    private String imgs;

    /** 直接依赖的模块 */
    @Excel(name = "直接依赖的模块")
    private String dependencie;

    /** 依赖的模块 */
    @Excel(name = "依赖的模块")
    private String dependencies;

    /** 简介 */
    private String description;

    /** 详情 */
    private String detail;

    /** 浏览数 */
    @Excel(name = "浏览数")
    private Long hit;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeTimes;

    /** 下载数 */
    @Excel(name = "下载数")
    private Long downloadTimes;

    /** URL */
    private String url;

    /** 压缩包路径 */
    @Excel(name = "压缩包路径")
    private String zipPath;

    /** 排序 */
    private Long sort;

    /** 作者ID */
    @Excel(name = "作者ID")
    private String authorIds;

    /** 收费类型 */
    @Excel(name = "收费类型")
    private Integer payType;

    /** 支付费用 */
    private Long payCount;

    /** 显示支付费用 */
    @Excel(name = "显示支付费用")
    private String payShow;

    /** 标志 */
    @Excel(name = "标志")
    private Integer status;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private Integer auditState;

    /** 审核不通过原因 */
    @Excel(name = "审核不通过原因")
    private String rejectReason;

    /** 发布时间 */
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    private boolean selected=false;//扩展字段
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setModuleState(Integer moduleState)
    {
        this.moduleState = moduleState;
    }

    public Integer getModuleState()
    {
        return moduleState;
    }
    public void setModuleType(String moduleType)
    {
        this.moduleType = moduleType;
    }

    public String getModuleType()
    {
        return moduleType;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getIcon()
    {
        return icon;
    }
    public void setCoverImg(String coverImg)
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg()
    {
        return coverImg;
    }
    public void setImgs(String imgs)
    {
        this.imgs = imgs;
    }

    public String getImgs()
    {
        return imgs;
    }
    public void setDependencie(String dependencie)
    {
        this.dependencie = dependencie;
    }

    public String getDependencie()
    {
        return dependencie;
    }
    public void setDependencies(String dependencies)
    {
        this.dependencies = dependencies;
    }

    public String getDependencies()
    {
        return dependencies;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getDetail()
    {
        return detail;
    }
    public void setHit(Long hit)
    {
        this.hit = hit;
    }

    public Long getHit()
    {
        return hit;
    }
    public void setLikeTimes(Long likeTimes)
    {
        this.likeTimes = likeTimes;
    }

    public Long getLikeTimes()
    {
        return likeTimes;
    }
    public void setDownloadTimes(Long downloadTimes)
    {
        this.downloadTimes = downloadTimes;
    }

    public Long getDownloadTimes()
    {
        return downloadTimes;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setZipPath(String zipPath)
    {
        this.zipPath = zipPath;
    }

    public String getZipPath()
    {
        return zipPath;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setAuthorIds(String authorIds)
    {
        this.authorIds = authorIds;
    }

    public String getAuthorIds()
    {
        return authorIds;
    }
    public void setPayType(Integer payType)
    {
        this.payType = payType;
    }

    public Integer getPayType()
    {
        return payType;
    }
    public void setPayCount(Long payCount)
    {
        this.payCount = payCount;
    }

    public Long getPayCount()
    {
        return payCount;
    }
    public void setPayShow(String payShow)
    {
        this.payShow = payShow;
    }

    public String getPayShow()
    {
        return payShow;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("name", getName())
            .append("moduleState", getModuleState())
            .append("moduleType", getModuleType())
            .append("icon", getIcon())
            .append("coverImg", getCoverImg())
            .append("imgs", getImgs())
            .append("dependencie", getDependencie())
            .append("dependencies", getDependencies())
            .append("description", getDescription())
            .append("detail", getDetail())
            .append("hit", getHit())
            .append("likeTimes", getLikeTimes())
            .append("downloadTimes", getDownloadTimes())
            .append("url", getUrl())
            .append("zipPath", getZipPath())
            .append("sort", getSort())
            .append("authorIds", getAuthorIds())
            .append("payType", getPayType())
            .append("payCount", getPayCount())
            .append("payShow", getPayShow())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
