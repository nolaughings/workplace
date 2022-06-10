DROP TABLE IF EXISTS `plugs_sys_pv`;
CREATE TABLE `plugs_sys_pv` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `module` varchar(50) DEFAULT NULL COMMENT '模块',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器',
  `referer` varchar(255) DEFAULT NULL COMMENT 'referer',
  `os` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `page_id` varchar(255) DEFAULT NULL COMMENT '页面内容ID',
  `page_title` varchar(255) DEFAULT NULL COMMENT '页面标题',
  `url` varchar(255) DEFAULT NULL,
  `device_type` varchar(50) DEFAULT NULL,
  `time_zone` varchar(10) DEFAULT NULL COMMENT '时区',
  `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址',
  `location` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2670 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `plugs_sys_pv_count`;
CREATE TABLE `plugs_sys_pv_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `day` date DEFAULT NULL COMMENT '日期',
  `count` int(11) DEFAULT NULL COMMENT '访问量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;


SELECT @parentId := LAST_INSERT_ID();
INSERT INTO sys_menu (`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ( 'PV', @parentId, '1', '/plugs/pv', '', 'C', '0', 'cms:pv:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2019-11-07 10:42:48', 'PV');
