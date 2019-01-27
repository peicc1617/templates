/*
Navicat MySQL Data Transfer

Source Server         : 58.206.100.146
Source Server Version : 50721
Source Host           : 58.206.100.146:3306
Source Database       : innovation_templates

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-01-27 11:40:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `projectID` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目的ID',
  `projectName` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '当前项目的创建时间',
  `editTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '项目的更新时间',
  `projectDesc` text,
  `projectTags` varchar(255) DEFAULT '',
  `openState` tinyint(1) DEFAULT NULL,
  `invitationCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_edge
-- ----------------------------
DROP TABLE IF EXISTS `project_edge`;
CREATE TABLE `project_edge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录的ID',
  `projectID` varchar(255) DEFAULT NULL COMMENT '记录对应的项目ID',
  `nodeI` varchar(255) DEFAULT NULL COMMENT '边的起始节点',
  `nodeJ` varchar(255) DEFAULT NULL COMMENT '边的结束节点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=460 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_log
-- ----------------------------
DROP TABLE IF EXISTS `project_log`;
CREATE TABLE `project_log` (
  `logID` int(11) NOT NULL AUTO_INCREMENT,
  `logType` varchar(255) DEFAULT NULL,
  `logMethod` varchar(255) DEFAULT NULL,
  `operateDate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userID` int(11) DEFAULT NULL,
  `projectID` int(11) DEFAULT NULL,
  `stepIndex` varchar(255) DEFAULT NULL,
  `nodeIndex` varchar(255) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=448 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_node
-- ----------------------------
DROP TABLE IF EXISTS `project_node`;
CREATE TABLE `project_node` (
  `projectID` int(11) NOT NULL COMMENT '节点所属的项目ID',
  `nodeIndex` varchar(255) NOT NULL COMMENT '节点在项目内的ID',
  `nodeName` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `state` tinyint(1) DEFAULT NULL,
  `nodeDesc` varchar(255) DEFAULT NULL,
  `goal` varchar(255) DEFAULT NULL,
  `summary` text,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `editTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `appName` varchar(255) DEFAULT NULL,
  `appPath` varchar(255) DEFAULT NULL,
  `templateProjectID` int(11) DEFAULT NULL,
  `lockState` tinyint(1) DEFAULT NULL,
  `stepIndex` varchar(255) DEFAULT NULL,
  `appIcon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nodeIndex`,`projectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_node_result
-- ----------------------------
DROP TABLE IF EXISTS `project_node_result`;
CREATE TABLE `project_node_result` (
  `projectID` int(11) NOT NULL,
  `nodeIndex` varchar(255) NOT NULL COMMENT '记录对应的节点ID',
  `userID` int(11) NOT NULL COMMENT '阶段内的APP排序字段',
  `state` varchar(255) DEFAULT NULL COMMENT '所使用的APP名称',
  `resultName` varchar(255) DEFAULT NULL,
  `resultKey` varchar(255) DEFAULT NULL,
  `editTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message` varchar(255) DEFAULT NULL,
  `resultID` int(11) DEFAULT NULL,
  PRIMARY KEY (`userID`,`nodeIndex`,`projectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_node_role
-- ----------------------------
DROP TABLE IF EXISTS `project_node_role`;
CREATE TABLE `project_node_role` (
  `projectID` int(11) NOT NULL,
  `nodeIndex` varchar(255) NOT NULL,
  `userID` int(11) NOT NULL,
  `nodeRole` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projectID`,`nodeIndex`,`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_person
-- ----------------------------
DROP TABLE IF EXISTS `project_person`;
CREATE TABLE `project_person` (
  `userID` int(11) NOT NULL,
  `projectID` int(11) NOT NULL,
  `au` int(11) DEFAULT NULL,
  `nodeIndex` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`projectID`,`nodeIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_role
-- ----------------------------
DROP TABLE IF EXISTS `project_role`;
CREATE TABLE `project_role` (
  `projectID` int(11) NOT NULL,
  `userID` int(255) NOT NULL,
  `projectRole` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projectID`,`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_step
-- ----------------------------
DROP TABLE IF EXISTS `project_step`;
CREATE TABLE `project_step` (
  `projectID` int(11) NOT NULL,
  `stepIndex` varchar(255) NOT NULL,
  `stepName` varchar(255) DEFAULT NULL,
  `stepDesc` varchar(255) DEFAULT NULL,
  `summary` text,
  `pos` int(11) DEFAULT NULL COMMENT '阶段的位置',
  PRIMARY KEY (`projectID`,`stepIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for refer
-- ----------------------------
DROP TABLE IF EXISTS `refer`;
CREATE TABLE `refer` (
  `referID` int(11) NOT NULL AUTO_INCREMENT,
  `referName` varchar(255) DEFAULT NULL,
  `nodes` text,
  `edges` text,
  `steps` text,
  `tags` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`referID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for ptest
-- ----------------------------
DROP PROCEDURE IF EXISTS `ptest`;
DELIMITER ;;
CREATE DEFINER=`xjtucad`@`%` PROCEDURE `ptest`()
BEGIN

DECLARE pid INT ;
SET pid = 10000 ;
WHILE pid > 0 DO
 INSERT INTO project (id, NAME)
VALUES
	(pid, 'jason') ; 
SET pid = pid - 1 ;
END
WHILE ; 
END
;;
DELIMITER ;
