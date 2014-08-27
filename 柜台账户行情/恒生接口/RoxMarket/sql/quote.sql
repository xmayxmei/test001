/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : quote

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-06-23 14:37:35
*/

SET FOREIGN_KEY_CHECKS=0;


-- ----------------------------
-- Table structure for `t_sh_5min`
-- ----------------------------
DROP TABLE IF EXISTS `t_sh_5min`;
CREATE TABLE `t_sh_5min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
  KEY `idx_sh_5min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sh_5min
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sh_daily`
-- ----------------------------
DROP TABLE IF EXISTS `t_sh_daily`;
CREATE TABLE `t_sh_daily` (
  `date` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`code`),
  KEY `idx_sh_daily_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sh_daily
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sh_monthly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sh_monthly`;
CREATE TABLE `t_sh_monthly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sh_monthly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sh_monthly
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sh_weekly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sh_weekly`;
CREATE TABLE `t_sh_weekly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sh_weekly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sh_weekly
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sh_yearly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sh_yearly`;
CREATE TABLE `t_sh_yearly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,2) DEFAULT NULL,
  `amount` decimal(30,2) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sh_yearly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sh_yearly
-- ----------------------------

-- ----------------------------

-- ----------------------------
-- Table structure for `t_sz_5min`
-- ----------------------------
DROP TABLE IF EXISTS `t_sz_5min`;
CREATE TABLE `t_sz_5min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sz_5min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sz_5min
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sz_daily`
-- ----------------------------
DROP TABLE IF EXISTS `t_sz_daily`;
CREATE TABLE `t_sz_daily` (
  `date` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`code`),
  KEY `idx_sz_daily_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sz_daily
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sz_monthly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sz_monthly`;
CREATE TABLE `t_sz_monthly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sz_monthly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sz_monthly
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sz_weekly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sz_weekly`;
CREATE TABLE `t_sz_weekly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sz_weekly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sz_weekly
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sz_yearly`
-- ----------------------------
DROP TABLE IF EXISTS `t_sz_yearly`;
CREATE TABLE `t_sz_yearly` (
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,2) DEFAULT NULL,
  `amount` decimal(30,2) DEFAULT NULL,
  PRIMARY KEY (`fromDate`,`toDate`,`code`),
  KEY `idx_sz_yearly_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sz_15min`;
CREATE TABLE `t_sz_15min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sz_15min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sh_15min`;
CREATE TABLE `t_sh_15min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sh_15min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sh_30min`;
CREATE TABLE `t_sh_30min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sh_30min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sz_30min`;
CREATE TABLE `t_sz_30min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sz_30min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_sz_30min`;
CREATE TABLE `t_sz_30min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sz_30min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sz_60min`;
CREATE TABLE `t_sz_60min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sz_60min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_sh_60min`;
CREATE TABLE `t_sh_60min` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `code` varchar(10) NOT NULL,
  `open` decimal(10,3) DEFAULT NULL,
  `close` decimal(10,3) DEFAULT NULL,
  `high` decimal(10,3) DEFAULT NULL,
  `low` decimal(10,3) DEFAULT NULL,
  `volume` decimal(30,3) DEFAULT NULL,
  `amount` decimal(30,3) DEFAULT NULL,
  PRIMARY KEY (`date`,`time`,`code`),
 KEY `idx_sh_60min_code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;