/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : trade

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-07-09 09:20:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_trade_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_trade_record`;
CREATE TABLE `t_trade_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clientId` varchar(15) DEFAULT NULL,
  `operate` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2043 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_trade_record
-- ----------------------------
