package com.ruoyi.plugs.framework.event;

/**
 * Created by markbro on 2018/11/20.
 * 自定义的系统事件
 */
public enum ApplicationEventDefined {

    ON_BEFORE_LOGIN("onBeforeLogin","用户登陆前"),
    ON_AFTER_LOGIN("onAfterLogin","用户登后"),
    ON_USER_CREATED("onUserCreated","用户创建完成"),
    ON_ROLE_CREATED("onRoleCreated","角色创建完成"),
    ON_UNIT_CREATED("onUnitCreated","机构创建完成"),
    ON_GROUP_CREATED("onGroupCreated","用户组创建完成"),
    ON_RESOURCE_CREATED("onResourceCreated","资源创建完成"),
    ON_SYSTEM_CONFIG_UPDATED("onSystemConfigUpdated","系统配置更新后"),
    ON_SCHEDULER_EXECUTED_BY_HAND("onSchedulerExecutedByHand","手工触发定时任务后"),
    ON_SCHEDULER_EXECUTED("onSchedulerExecuted","定时任务执行后"),
    ON_AFTER_SIGN_IN_BADGE("onAfterSignInBadge","用户签到后获得签到勋章"),
    ON_SAVE_SCORE_HISTORY("onSaveScoreHistory","保存积分日志记录"),
    ON_LOTTERY_VIP("onLotteryVip","积分抽奖获取VIP"),
    ON_GITEE_STAR_WATCH_FORK("onLotteryVip","Gitee点赞关注Fork");
    private String value;
    private String description;
    private ApplicationEventDefined(String value, String description){
        this.value=value;
        this.description=description;
    }
    public String getDescription(){
        return description;
    }
    public String getValue(){
        return value;
    }
    @Override
    public String toString() {
        return value;
    }
}
