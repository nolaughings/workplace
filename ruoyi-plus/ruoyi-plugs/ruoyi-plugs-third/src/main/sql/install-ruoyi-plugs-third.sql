DROP TABLE IF EXISTS `plugs_third_ai_his`;
CREATE TABLE `plugs_third_ai_his(
  `idvarchar(50) NOT NULL COMMENT 'ID',
  `yhidvarchar(50) DEFAULT NULL COMMENT '用户ID',
  `yhmcvarchar(50) DEFAULT NULL COMMENT '用户名称',
  `ai_typevarchar(50) DEFAULT NULL COMMENT '类型',
  `type_namevarchar(50) DEFAULT NULL COMMENT '类型名称',
  `resultvarchar(20) DEFAULT NULL COMMENT '结果1成功0失败',
  `error_msgtext COMMENT '错误信息',
  `json_resulttext COMMENT '请求结果',
  `create_timedatetime DEFAULT NULL COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `plugs_third_sms_his`;
CREATE TABLE `plugs_third_sms_his(
  `idint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `yhidvarchar(50) DEFAULT NULL COMMENT '用户ID',
  `yhmcvarchar(50) DEFAULT NULL COMMENT '用户名称',
  `carrieroperatorvarchar(50) DEFAULT NULL COMMENT '运营商',
  `phonevarchar(45) DEFAULT NULL COMMENT '手机号',
  `contentvarchar(500) DEFAULT NULL COMMENT '内容',
  `returncodevarchar(200) DEFAULT NULL COMMENT '返回码',
  `createTimedatetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



SELECT @parentId := LAST_INSERT_ID();
INSERT INTO sys_menu ( `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ( '第三方',@parentId, '1', '#', 'menuItem', 'M', '0', '', 'fa fa-life-saver', 'admin', '2019-10-11 14:35:27', 'admin', '2021-03-12 14:45:51', '第三方');
SELECT @parentIdThrid := LAST_INSERT_ID();

INSERT INTO  sys_menu ( `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('短信发送历史', @parentIdThrid, '1', '/third/smsHis', 'menuItem', 'C', '0', 'third:smsHis:view', '#', 'admin', '2019-10-11 14:36:28', '', '2020-09-06 13:16:16', '');
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('API测试',@parentIdThrid, '1', '/third/api', 'menuItem', 'C', '0', 'third:api:view', '#', 'admin', '2019-10-11 14:36:28', '', '2020-09-06 13:16:18', '');
INSERT INTO sys_menu( `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('百度AI', @parentIdThrid, '1', '/third/ai', 'menuItem', 'C', '0', 'third:ai', '#', 'admin', '2019-10-11 14:36:28', '', '2020-09-06 13:16:20', '');
INSERT INTO  sys_menu (`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('智能识别历史', @parentIdThrid, '1', '/third/aiHis', 'menuItem', 'C', '0', 'third:aiHis:view', '#', 'admin', '2019-10-11 14:36:28', '', '2020-09-06 13:16:22', '');
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('发送短信', @parentIdThrid, '1', '#', 'menuItem', 'F', '0', 'third:api:sendSms', '#', 'admin', '2019-11-07 15:46:58', '', '2019-11-07 10:42:48', '');
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('查询IP', @parentIdThrid, '2', '#', 'menuItem', 'F', '0', 'third:api:queryIp', '#', 'admin', '2019-11-07 15:47:29', '', '2019-11-07 10:42:48', '');
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('百度推送', @parentIdThrid, '3', '#', 'menuItem', 'F', '0', 'third:api:baiduPush', '#', 'admin', '2019-11-07 15:47:53', '', '2019-11-07 10:42:48', '');

INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('人工智能识别类型', 'ai_type', '0', 'markbro', '2019-10-12 17:55:24', 'markbro', '2019-10-28 20:05:34', '');
INSERT INTO sys_dict_type(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('百度推送类型', 'baidu_push_type', '0', 'markbro', '2019-10-12 17:55:24', 'markbro', '2019-10-12 17:55:24', '');

INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '人脸检测', 'faceDetect', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:10', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '植物识别', 'plant', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:25', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '银行卡识别', 'bankCard', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:37', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('4', '身份证识别', 'idCard', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:48', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('5', '车牌号识别', 'plate', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:01', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('6', '驾驶证识别', 'driver', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:14', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('7', '动物识别', 'animal', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:26', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('8', '车型识别', 'car', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:43', 'admin', '2019-10-12 17:58:58', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('9', '菜品识别', 'dish', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:59:09', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('10', '通用文字识别', 'general_basic', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:59:21', '');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('1', '推送', 'urls', 'baidu_push_type', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2', '更新', 'update', 'baidu_push_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态');
INSERT INTO sys_dict_data(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('3', '删除', 'del', 'baidu_push_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态');





