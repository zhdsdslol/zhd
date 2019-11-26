/*
 Navicat Premium Data Transfer

 Source Server         : mymysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : my

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 26/11/2019 10:06:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for addqqb
-- ----------------------------
DROP TABLE IF EXISTS `addqqb`;
CREATE TABLE `addqqb`  (
  `number` int(10) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addtime` datetime(0) NOT NULL,
  `userid` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(10) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `talk` int(10) DEFAULT 0,
  `dianzan` int(10) DEFAULT 0,
  `see` int(10) DEFAULT 0,
  `addtime` datetime(0) DEFAULT NULL,
  `time` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '全部',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '发布者未填写介绍！',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movies
-- ----------------------------
INSERT INTO `movies` VALUES ('为什么要吃鳄鱼', 90, '1574644827525.mp4', '1574644827505.jpg', 1, '钟鸿达', 0, 0, 0, '2019-11-25 09:20:28', '00:6', '搞笑', '发布者未填写介绍！');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `loginname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `qqb` int(10) UNSIGNED ZEROFILL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'zhd', '6301664', '钟鸿达', 0000000999);
INSERT INTO `users` VALUES (2, 'admin', '88888888', '管理员', 0000000999);

SET FOREIGN_KEY_CHECKS = 1;
