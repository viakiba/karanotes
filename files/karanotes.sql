/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : karanotes

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-03-08 12:36:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for articleabstract
-- ----------------------------
DROP TABLE IF EXISTS `articleabstract`;
CREATE TABLE `articleabstract` (
  `article_id` varchar(50) NOT NULL,
  `article_title` varchar(500) DEFAULT NULL,
  `article_show_img` varchar(255) DEFAULT NULL,
  `classify_id` varchar(50) DEFAULT NULL,
  `abstract_content` varchar(500) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `collect_num` int(11) DEFAULT '0',
  `read_num` int(11) DEFAULT '0',
  `praise_num` int(11) DEFAULT '0',
  `article_attachment` varchar(500) DEFAULT NULL,
  `article_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `article_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `extra` json DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for articleclassify
-- ----------------------------
DROP TABLE IF EXISTS `articleclassify`;
CREATE TABLE `articleclassify` (
  `classify_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `classify_content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`classify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for articlecontent
-- ----------------------------
DROP TABLE IF EXISTS `articlecontent`;
CREATE TABLE `articlecontent` (
  `article_id` varchar(50) NOT NULL,
  `article_content` text,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for articlepraise
-- ----------------------------
DROP TABLE IF EXISTS `articlepraise`;
CREATE TABLE `articlepraise` (
  `praise_id` varchar(50) NOT NULL,
  `article_id` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `praise_user_id` varchar(50) DEFAULT NULL,
  `praise_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`praise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for articletag
-- ----------------------------
DROP TABLE IF EXISTS `articletag`;
CREATE TABLE `articletag` (
  `article_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `tag_content` json DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collectinfo
-- ----------------------------
DROP TABLE IF EXISTS `collectinfo`;
CREATE TABLE `collectinfo` (
  `collect_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `article_id` varchar(50) DEFAULT NULL,
  `collect_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for commentinfo
-- ----------------------------
DROP TABLE IF EXISTS `commentinfo`;
CREATE TABLE `commentinfo` (
  `comment_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `article_id` varchar(50) DEFAULT NULL,
  `comment_content` text,
  `comment_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `reply_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for faqinfo
-- ----------------------------
DROP TABLE IF EXISTS `faqinfo`;
CREATE TABLE `faqinfo` (
  `faq_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `faq_content` text,
  `faq_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`faq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for followinfo
-- ----------------------------
DROP TABLE IF EXISTS `followinfo`;
CREATE TABLE `followinfo` (
  `follow_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `follow_user_id` varchar(50) DEFAULT NULL,
  `is_eachother` tinyint(1) DEFAULT NULL,
  `follow_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`follow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notifiinfo
-- ----------------------------
DROP TABLE IF EXISTS `notifiinfo`;
CREATE TABLE `notifiinfo` (
  `user_id` varchar(50) NOT NULL,
  `comment_read_time` datetime DEFAULT NULL,
  `praise_read_time` datetime DEFAULT NULL,
  `collect_read_time` datetime DEFAULT NULL,
  `follow_read_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_id` varchar(50) NOT NULL,
  `user_headimg` varchar(100) DEFAULT NULL,
  `user_background_img` varchar(255) DEFAULT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_github` varchar(500) DEFAULT NULL,
  `user_sex` int(11) DEFAULT NULL,
  `user_path` varchar(100) DEFAULT NULL,
  `user_signature` varchar(500) DEFAULT NULL,
  `user_extra` json DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userlogin
-- ----------------------------
DROP TABLE IF EXISTS `userlogin`;
CREATE TABLE `userlogin` (
  `token_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `login_device_type` varchar(50) DEFAULT NULL,
  `user_login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TRIGGER IF EXISTS `create_userinfo`;
DELIMITER ;;
CREATE TRIGGER `create_userinfo` AFTER INSERT ON `userinfo` FOR EACH ROW begin 
			insert into notifiinfo values(new.user_id,NOW(),NOW(),NOW(),NOW());
			insert into articleclassify values(new.user_id,new.user_id,"默认分类");
END
;;
DELIMITER ;
