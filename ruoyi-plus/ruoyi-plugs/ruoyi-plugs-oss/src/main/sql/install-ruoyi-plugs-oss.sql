DROP TABLE IF EXISTS `plugs_sys_oss`;
CREATE TABLE `plugs_sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(64) NOT NULL DEFAULT '' COMMENT '文件名',
  `file_suffix` varchar(10) NOT NULL DEFAULT '' COMMENT '文件后缀名',
  `url` varchar(200) NOT NULL COMMENT 'URL地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '上传人',
  `service` tinyint(2) NOT NULL DEFAULT '1' COMMENT '服务商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文件上传';

SELECT @parentId := LAST_INSERT_ID();
INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ( 'OSS文件管理',@parentId, '1', '/system/oss', 'menuItem', 'C', '0', 'system:oss:view', '#', 'admin', '2018-11-16 13:59:45', 'admin', '2019-12-23 14:03:43', 'OSS文件管理');
