
DROP TABLE IF EXISTS `spider_config`;
CREATE TABLE `spider_config(
  `idint(11) NOT NULL AUTO_INCREMENT COMMENT '爬虫配置ID',
  `spider_codevarchar(50) DEFAULT NULL COMMENT '爬虫编码',
  `spider_namevarchar(50) DEFAULT NULL COMMENT '爬虫名称',
  `entry_urlstext COMMENT '入口地址',
  `target_regexvarchar(255) DEFAULT NULL COMMENT '目标URL正则',
  `cascadesmallint(6) DEFAULT '0' COMMENT '1:级联发现url  0:只从入口页面发现url',
  `table_namevarchar(50) DEFAULT NULL COMMENT '存储的表名',
  `domainvarchar(255) DEFAULT NULL COMMENT '网站根域名',
  `charsetvarchar(50) DEFAULT 'utf8' COMMENT '字符集',
  `sleep_timeint(11) DEFAULT '1000' COMMENT '睡眠时间(ms)',
  `retry_timessmallint(6) DEFAULT '1' COMMENT '重试次数',
  `thread_countsmallint(255) DEFAULT '1' COMMENT '线程数量',
  `use_proxysmallint(6) DEFAULT '0' COMMENT '使用代理',
  `show_logsmallint(6) DEFAULT '1' COMMENT '打印日志',
  `save_dbsmallint(6) DEFAULT '0' COMMENT '保存到数据库',
  `is_jsonsmallint(6) DEFAULT '0' COMMENT '是否json接口请求',
  `spider_high_settingvarchar(100) DEFAULT NULL COMMENT '爬虫高级设置',
  `user_define_pipelinevarchar(255) DEFAULT NULL COMMENT '用户自定义pepeline',
  `is_seleniumsmallint(6) DEFAULT '0',
  `mission_finishvarchar(255) DEFAULT NULL COMMENT '任务结束回调',
  `list_extract_byvarchar(100) DEFAULT NULL COMMENT '列表提取规则',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `spider_field`;
CREATE TABLE `spider_field(
  `field_idint(11) NOT NULL AUTO_INCREMENT COMMENT '字段ID',
  `config_idint(11) DEFAULT NULL COMMENT '爬虫配置ID',
  `fieldvarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字段',
  `field_namevarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字段名称',
  `extract_typevarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '提取类型',
  `extract_byvarchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '提取规则2',
  `extract_by2varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '提取规则',
  `constant_valuetext CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT '常量值',
  `extract_indexvarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '元素的索引',
  `process_rule_idvarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '处理规则',
  `extract_attr_flagvarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '是否是根据元素取值',
  `extract_attrvarchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '根据哪个元素取值',
  `spider_field_high_settingvarchar(100) DEFAULT NULL COMMENT '内置字段处理机制',
  `spider_field_high_setting_paramsvarchar(255) DEFAULT NULL COMMENT '内置字段处理机制参数',
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `spider_filed_rule`;
CREATE TABLE `spider_filed_rule(
  `idint(11) NOT NULL AUTO_INCREMENT,
  `field_idvarchar(50) DEFAULT NULL COMMENT '字段ID',
  `process_typevarchar(50) DEFAULT NULL COMMENT '数据处理规则',
  `replaceRegtext COMMENT '替换正则',
  `replacementvarchar(255) DEFAULT NULL COMMENT '替换内容',
  `substr_targetvarchar(50) DEFAULT NULL COMMENT '截取字符串目标',
  `sortint(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_mission`;
CREATE TABLE `spider_mission(
  `mission_idint(11) NOT NULL AUTO_INCREMENT COMMENT '爬虫任务ID',
  `mission_namevarchar(50) DEFAULT NULL COMMENT '任务名称',
  `spider_config_idint(11) DEFAULT NULL COMMENT '爬虫配置ID',
  `entry_urlstext COMMENT '入口地址',
  `statusvarchar(50) DEFAULT NULL COMMENT '任务状态',
  `start_timedatetime DEFAULT NULL COMMENT '开始时间',
  `end_timedatetime DEFAULT NULL COMMENT '结束时间',
  `time_costmediumtext COMMENT '爬取时间(单位秒)',
  `exit_wayvarchar(50) DEFAULT NULL COMMENT '退出方式。DEFAULT，DURATION，URL_COUNT。',
  `exit_way_countint(11) DEFAULT NULL COMMENT '退出方式值',
  `success_numint(11) DEFAULT NULL COMMENT '爬取数量',
  `cookie_strtext,
  `header_strtext,
  `dept_idvarchar(255) DEFAULT NULL COMMENT '部门ID',
  `user_idvarchar(255) DEFAULT NULL COMMENT '用户ID',
  `create_byvarchar(50) DEFAULT NULL COMMENT '创建人',
  `create_timedatetime DEFAULT NULL,
  `loop_flagsmallint(6) DEFAULT '0' COMMENT '翻页标志',
  `loop_paramvarchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '翻页参数  如URL 后追加 &page=5来控制翻页',
  `loop_numsmallint(6) DEFAULT NULL COMMENT '循环次数 URL翻页用',
  PRIMARY KEY (`mission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;


SELECT @parentId := LAST_INSERT_ID();
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫模块', '0', '6', '#', 'menuItem', 'M', '0', '', 'fa fa-bug', 'ayueyue', '2019-11-11 14:48:32', 'ayueyue', '2021-03-12 14:45:46', '');
SELECT @parentIdSpider := LAST_INSERT_ID();
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫任务', @parentIdSpider, '1', '/spider/spiderMission', 'menuItem', 'C', '0', 'spider:spiderMission:view', '#', 'ayueyue', '2019-11-11 14:50:49', '', '2020-09-22 20:37:26', '');
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫配置', @parentIdSpider, '2', '/spider/spiderConfig', 'menuItem', 'C', '0', 'spider:spiderConfig:view', '#', 'ayueyue', '2019-11-11 14:51:44', '', '2020-09-22 20:37:29', '');

INSERT INTO sys_config(`config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫使用谷歌浏览器渲染页面驱动路径', 'webdriver.chrome.driver', 'd:\\chromedriver.exe', 'N', 'admin', '2021-10-29 23:13:48', 'admin', '2021-10-29 23:13:52', NULL);


INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫任务状态', 'spider_mission_status', '0', 'markbro', '2019-11-08 10:41:04', 'markbro', '2019-11-08 10:41:04', '');
INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫退出方式', 'spider_exit_way', '0', 'markbro', '2019-11-08 10:41:04', 'markbro', '2019-11-08 10:41:04', '');
INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫内容提取类型', 'spider_extract_type', '0', 'markbro', '2019-11-08 10:41:04', 'markbro', '2019-11-08 10:41:04', '');
INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('爬虫值处理规则', 'field_value_process_type', '0', 'markbro', '2019-11-08 10:41:04', 'markbro', '2019-11-08 10:41:04', '爬虫字段值处理规则');
INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('内置字段处理规则', 'spider_field_high_setting', '0', 'markbro', '2019-11-08 10:41:04', 'markbro', '2019-11-08 10:41:04', NULL);


INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '待执行', 'wait', 'spider_mission_status', '', 'info', 'Y', '0', 'ayueyue', '2019-11-11 14:23:39', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '执行中', 'running', 'spider_mission_status', '', 'success', 'N', '0', 'ayueyue', '2019-11-11 14:24:20', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '完成', 'done', 'spider_mission_status', '', 'primary', 'N', '0', 'ayueyue', '2019-11-11 14:25:16', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('4', '错误', 'error', 'spider_mission_status', '', 'danger', 'N', '0', 'ayueyue', '2019-11-11 14:26:29', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '默认', 'DEFAULT', 'spider_exit_way', '', 'default', 'Y', '0', 'ayueyue', '2019-11-11 15:02:25', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '持续时间', 'DURATION', 'spider_exit_way', '', 'primary', 'N', '0', 'ayueyue', '2019-11-11 15:04:01', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '链接计数', 'URL_COUNT', 'spider_exit_way', '', 'success', 'N', '0', 'ayueyue', '2019-11-11 15:05:06', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', 'Xpath', 'xpath', 'spider_extract_type', '', 'primary', 'Y', '0', 'ayueyue', '2019-11-12 10:14:26', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', 'Css', 'css', 'spider_extract_type', '', 'success', 'N', '0', 'ayueyue', '2019-11-12 10:14:42', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '常量', 'constant', 'spider_extract_type', '', 'warning', 'N', '0', 'ayueyue', '2019-11-12 10:15:07', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '替换', 'replace', 'field_value_process_type', '', 'primary', 'Y', '0', 'ayueyue', '2019-11-14 17:01:32', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '截取之前', 'substrbefore', 'field_value_process_type', '', 'success', 'N', '0', 'ayueyue', '2019-11-14 17:01:54', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '截取之后', 'substrafter', 'field_value_process_type', '', 'info', 'N', '0', 'ayueyue', '2019-11-14 17:02:17', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('4', '取中间', 'middle', 'field_value_process_type', '', 'danger', 'N', '0', 'ayueyue', '2019-11-17 12:34:22', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('5', '替换掉html标签', 'replace_html', 'field_value_process_type', NULL, NULL, 'N', '0', 'ayueyue', '2021-07-14 10:46:33', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('6', '替换掉a标签', 'replace_a', 'field_value_process_type', '', '', 'N', '0', 'ayueyue', '2021-07-14 11:25:47', 'ayueyue', '2021-07-14 13:20:01', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('7', '预留', NULL, 'field_value_process_type', '', 'success', 'N', '1', 'ayueyue', '2019-11-17 12:34:45', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('8', '预留', NULL, 'field_value_process_type', '', '', 'N', '1', 'ayueyue', '2019-11-17 12:35:16', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('9', '预留', NULL, 'field_value_process_type', '', 'success', 'N', '1', 'ayueyue', '2019-11-17 12:34:45', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('10', '预留', NULL, 'field_value_process_type', '', '', 'N', '1', 'ayueyue', '2019-11-17 12:35:16', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '无', '0', 'spider_field_high_setting', NULL, NULL, 'N', '0', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '下载文件', '1', 'spider_field_high_setting', NULL, NULL, 'N', '0', 'ayueyue', '2021-07-14 13:32:51', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '下载内容详情图片', '2', 'spider_field_high_setting', NULL, NULL, 'N', '0', 'ayueyue', '2021-07-14 13:33:19', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('4', '自动选取封面图片', '3', 'spider_field_high_setting', NULL, NULL, 'N', '0', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('5', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('6', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('7', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('8', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('9', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('10', '保留', '0', 'spider_field_high_setting', NULL, NULL, 'N', '1', 'ayueyue', '2021-07-14 13:33:45', '', NULL, NULL);
