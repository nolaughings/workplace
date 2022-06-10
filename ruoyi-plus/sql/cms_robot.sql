/*
Navicat MySQL Data Transfer

Source Server         : txl
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ruoyi-cms

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2022-06-10 00:29:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_robot
-- ----------------------------
DROP TABLE IF EXISTS `cms_robot`;
CREATE TABLE `cms_robot` (
  `robot_id` int(50) NOT NULL AUTO_INCREMENT COMMENT '机器人编号',
  `robot_img` varchar(100) NOT NULL COMMENT '机器人照片',
  `robot_name` varchar(50) NOT NULL COMMENT '机器人名称',
  `robot_function` varchar(255) NOT NULL COMMENT '机器人功能',
  PRIMARY KEY (`robot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_robot
-- ----------------------------
INSERT INTO `cms_robot` VALUES ('2', 'http://localhost/profile/upload/2022/06/09/c32dbb46-c61a-4f6e-98c8-16ac1ec21b3c.jpg', '室内巡检机器人', 'http://localhost/profile/upload/2022/06/09/87b0264a-dc51-47b1-950c-eecd57b3efd6.png');
