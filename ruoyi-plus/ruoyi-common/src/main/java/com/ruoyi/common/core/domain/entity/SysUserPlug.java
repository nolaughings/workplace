package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;


public class SysUserPlug extends BaseEntity {

    /*个人说明*/
    private String description;
    /*邮箱验证标志*/
    private Integer emailFlag;
    /*手机验证标志*/
    private Integer phoneFlag;

    private Integer score;
    private String giteeAccount;//gitee账户
    private String thirdAccount;//扩展字段，用户第三方登录的账号，不同于openid
    private Integer money;//余额

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmailFlag() {
        return emailFlag;
    }

    public void setEmailFlag(Integer emailFlag) {
        this.emailFlag = emailFlag;
    }

    public Integer getPhoneFlag() {
        return phoneFlag;
    }

    public void setPhoneFlag(Integer phoneFlag) {
        this.phoneFlag = phoneFlag;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getGiteeAccount() {
        return giteeAccount;
    }

    public void setGiteeAccount(String giteeAccount) {
        this.giteeAccount = giteeAccount;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


}
