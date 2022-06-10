DROP TABLE IF EXISTS `plugs_sys_holiday`;
CREATE TABLE `plugs_sys_holiday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` varchar(4) DEFAULT NULL COMMENT '年份',
  `code` varchar(50) DEFAULT NULL COMMENT '节日简码',
  `name` varchar(50) DEFAULT NULL COMMENT '节日名称',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `activity_start_date` date DEFAULT NULL COMMENT '活动开始日期',
  `activity_end_date` date DEFAULT NULL COMMENT '活动结束日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

SELECT @parentId := LAST_INSERT_ID();
INSERT INTO  sys_menu (`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('节假日配置', @parentId, '1', '/plugs/sysHoliday', '', 'C', '0', 'plugs:sysHoliday:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '节假日配置');
SELECT @parentIdHoliday := LAST_INSERT_ID();
INSERT INTO sys_menu ( `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('节假日查询', @parentIdHoliday, '1', '#', '', 'F', '0', 'plugs:sysHoliday:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
