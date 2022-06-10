package com.ruoyi.plugs.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    public static final Integer STATUS_NORMAL=0;

    public static final Integer STATUS_UN_NORMAL=1;
    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 参数管理 cache name
     */
    public static final String SYS_CONFIG_CACHE = "sys-config";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache name
     */
    public static final String SYS_DICT_CACHE = "sys-dict";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * uid
     */
    public static final String FRONT_COOKIE_UID = "uid";

    /**
     * 预览资源映射路径 前缀
     */
    public static final String PREVIEW_PREFIX = "/preview";

    /**
     * 文件分隔符
     */
    public static final String SYS_FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * 当前数据库链接的数据库名
     */
    public static String DB_NAME = "";
    public static final String HTTP = "http";
    public static final String MODULE_CODE_VIP = "vip";

    public static final String KEY_COOKIE_USER_CONFIG = "user_config";

    public static final String KEY_PAGE_STATICS_COUNT = "pageStaticsCount";//pv
    public static final String KEY_PAGE_STATICS_USERS = "pageStaticsUsers";//用户数
    public static final String KEY_PAGE_STATICS_ARTICLES = "pageStaticsArticles";//文章数
    public static final String KEY_PAGE_STATICS_TODAY = "pageStaticsToday";//本日新增
    public static final String KEY_PAGE_STATICS_WEEK = "pageStaticsWeek";//本周新增
    public static final String KEY_PAGE_STATICS_RUNNING = "pageStaticsRunning";//系统运行天数

}
