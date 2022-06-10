DROP TABLE IF EXISTS `plugs_sys_cost_time`;
CREATE TABLE `plugs_sys_cost_time` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `spend_time` int(11) DEFAULT NULL COMMENT '消耗的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `plugs_sys_event_log`;
CREATE TABLE `plugs_sys_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `event_code` varchar(50) DEFAULT NULL COMMENT '时间编号',
  `event_name` varchar(100) DEFAULT NULL COMMENT '时间名称',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  `datas` text COMMENT '参数',
  `result` smallint(6) DEFAULT '1' COMMENT '结果-失败0,1-成功',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `flag` smallint(6) DEFAULT NULL COMMENT '标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `plugs_sys_module`;
CREATE TABLE `plugs_sys_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `code` varchar(20) DEFAULT NULL COMMENT '模块代码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `version` varchar(20) DEFAULT NULL COMMENT '版本号',
  `module_state` smallint(6) DEFAULT NULL COMMENT '模块状态',
  `module_type` varchar(10) DEFAULT NULL COMMENT '类型(sys系统必备plug插件app独立应用)',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `cover_img` varchar(255) DEFAULT NULL COMMENT '封面图片',
  `imgs` varchar(255) DEFAULT NULL COMMENT '截图',
  `dependencie` varchar(255) DEFAULT NULL COMMENT '直接依赖的模块',
  `dependencies` varchar(255) DEFAULT NULL COMMENT '依赖的模块',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `detail` text COMMENT '详情',
  `hit` int(11) DEFAULT NULL COMMENT '浏览数',
  `like_times` int(11) DEFAULT NULL COMMENT '点赞数',
  `download_times` int(11) DEFAULT NULL COMMENT '下载数',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL',
  `zip_path` varchar(255) DEFAULT NULL COMMENT '压缩包路径',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `author_ids` varchar(11) DEFAULT NULL COMMENT '作者ID',
  `pay_type` smallint(6) DEFAULT '0' COMMENT '收费类型',
  `pay_count` double DEFAULT '0' COMMENT '支付费用',
  `pay_show` varchar(50) DEFAULT '免费' COMMENT '显示支付费用',
  `status` smallint(6) DEFAULT '0' COMMENT '标志0正常1停用',
  `audit_state` smallint(6) DEFAULT '0' COMMENT '审核状态',
  `reject_reason` varchar(255) DEFAULT NULL COMMENT '审核不通过原因',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `plugs_sys_user_invite`;
CREATE TABLE `plugs_sys_user_invite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `account` varchar(50) DEFAULT NULL COMMENT '用户登录名称',
  `invite_user_id` varchar(50) DEFAULT NULL COMMENT '邀请人用户ID',
  `ip` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
