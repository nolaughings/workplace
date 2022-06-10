/*
Navicat MySQL Data Transfer

Source Server         : txl
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ruoyi-cms

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2022-06-10 00:29:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_news
-- ----------------------------
DROP TABLE IF EXISTS `cms_news`;
CREATE TABLE `cms_news` (
  `news_id` int(50) NOT NULL AUTO_INCREMENT COMMENT '新闻编号',
  `news_img` varchar(100) NOT NULL COMMENT '新闻照片',
  `news_title` varchar(50) NOT NULL COMMENT '新闻标题',
  `news_content` varchar(500) NOT NULL COMMENT '新闻内容',
  `news_publish` int(100) NOT NULL COMMENT '新闻发布状态',
  `news_publishdate` datetime(6) DEFAULT NULL COMMENT '新闻发布时间',
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_news
-- ----------------------------
INSERT INTO `cms_news` VALUES ('2', 'http://localhost/profile/upload/2022/06/08/48646384-9501-4501-aa73-f2098474322f.png', '宝山区政府邀请檀索公司参加建党100周年庆祝活动', '2021年7月26日，上海檀索科技有限公司参加宝山区 | 退役军人创业创新大赛暨市第二届退役军人创业创新大赛。宝山区退役军人事务局负责同志介绍了退役军人创业创新大赛的背景意义、筹备过程、各方支持等情况，并表示将对退役军人企业提供更多支持和优质服务，助力退役军人创业创新，激发退役军人双创热情，展现退役军人激情创业、睿智创新的风采。', '0', '2021-07-26 00:00:00.000000');
INSERT INTO `cms_news` VALUES ('3', 'http://localhost/profile/upload/2022/06/08/374d3f51-3a4b-4f9e-b2f7-7f025760ba08.png', '领导莅临檀索，结合新技术解决“老问题”', '2021年7月14日下午，宝山区城市数字化转型推进大会”在中国宝武钢铁会博中心举行。我司作为应邀企业参与了此次的推进大会，会上陈杰书记表示，希望宝山区的新老创新企业，多为宝山区的数字化转型做出贡献。', '0', '2021-07-14 00:00:00.000000');
INSERT INTO `cms_news` VALUES ('4', 'http://localhost/profile/upload/2022/06/08/c5043763-8fd6-4cad-b2e6-11d457ef1b47.png', '上海市大学生科技创业基金会的创业基金支持', '2021年6月25日，上海檀索科技有限公司的立案项目经过专家多轮的评审审核，成功申请上海市大学生科技创业基金会的创业基金支持。上海市大学生科技创业基金会（ 简称创业基金会或EFG）成立于2006年8月，是由上海市政府发起的国内首家传播创业文化、支持创业实践的公益机构；上海市大学生科技创业基金（简称“天使基金”）是专注于扶持大学生青年创新创业的公益基金。', '0', '2021-06-26 00:00:00.000000');
