DROP TABLE IF EXISTS `plugs_sys_third_oauth`;
CREATE TABLE `plugs_sys_third_oauth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `openid` varchar(50) DEFAULT NULL COMMENT '第三方平台用户ID',
  `login_type` varchar(50) DEFAULT NULL COMMENT '登录类型',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
