/*
 Navicat Premium Data Transfer

 Source Server         : TencentSQL
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 129.211.79.163:3306
 Source Schema         : foodcare

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 30/06/2019 11:40:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '呢称',
  `age` int(11) NULL DEFAULT NULL,
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `height` double(6, 2) NULL DEFAULT NULL COMMENT '厘米为单位',
  `weight` double(6, 2) NULL DEFAULT NULL COMMENT '千克为单位',
  `fatRate` double(3, 2) NULL DEFAULT NULL COMMENT '体脂率',
  `picture` blob NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for diet
-- ----------------------------
DROP TABLE IF EXISTS `diet`;
CREATE TABLE `diet`  (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `group` int(3) NOT NULL COMMENT '类别（早餐，午餐，晚餐）',
  `date` datetime(6) NOT NULL COMMENT '创建的日期',
  `account_id` int(255) NOT NULL COMMENT '指向所有者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account`(`account_id`) USING BTREE,
  CONSTRAINT `diet_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dietdetail
-- ----------------------------
DROP TABLE IF EXISTS `dietdetail`;
CREATE TABLE `dietdetail`  (
  `diet_id` int(5) NOT NULL,
  `food_id` int(5) NOT NULL,
  `quantity` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`diet_id`, `food_id`) USING BTREE,
  INDEX `diet`(`diet_id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `dietdetail_ibfk_1` FOREIGN KEY (`diet_id`) REFERENCES `diet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dietdetail_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dishes
-- ----------------------------
DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes`  (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜肴所属菜系（如广东菜）',
  `heat` int(5) NULL DEFAULT NULL COMMENT '热量(千卡)',
  `tanshui` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '碳水化合物',
  `fat` double(8, 2) NULL DEFAULT NULL COMMENT '脂肪',
  `protein` double(6, 2) NULL DEFAULT NULL COMMENT '蛋白质',
  `cellulose` double(6, 2) NULL DEFAULT NULL COMMENT '纤维素',
  `measure` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '度量单位（一碗汤多少千卡）',
  `ingredients` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '主料',
  `excipient` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '辅料',
  `practice` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '做法（步骤）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `group` int(1) NULL DEFAULT NULL COMMENT '说明为菜品或者食品,0为菜品',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `picture` blob NULL COMMENT '食品或者菜品的图片',
  `category` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '食品类别（肉类、蛋类）',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜品类别(广东菜)',
  `heat` int(6) NULL DEFAULT NULL,
  `tanshui` double(6, 2) NULL DEFAULT NULL COMMENT '碳水化合物',
  `fat` double(6, 2) NULL DEFAULT NULL COMMENT '脂肪',
  `protein` double(8, 2) NULL DEFAULT NULL COMMENT '蛋白质',
  `cellulose` double(6, 2) NULL DEFAULT NULL COMMENT '纤维素',
  `vitaminA` double(8, 2) NULL DEFAULT NULL,
  `vitaminC` double(8, 2) NULL DEFAULT NULL,
  `vitaminE` double(8, 2) NULL DEFAULT NULL,
  `carotene` double(8, 2) NULL DEFAULT NULL COMMENT '胡萝卜素',
  `liuan` double(8, 2) NULL DEFAULT NULL COMMENT '硫胺素',
  `hehuang` double(8, 2) NULL DEFAULT NULL COMMENT '核黄素',
  `yansuan` double(8, 2) NULL DEFAULT NULL COMMENT '烟酸',
  `cholesterol` int(8) NULL DEFAULT NULL COMMENT '胆固醇',
  `mei` double(6, 2) NULL DEFAULT NULL,
  `gai` double(6, 2) NULL DEFAULT NULL,
  `tie` double(6, 2) NULL DEFAULT NULL,
  `xin` double(6, 2) NULL DEFAULT NULL,
  `tong` double(6, 2) NULL DEFAULT NULL,
  `meng` double(6, 2) NULL DEFAULT NULL,
  `jia` double(6, 2) NULL DEFAULT NULL,
  `lin` double(6, 2) NULL DEFAULT NULL,
  `na` double(6, 2) NULL DEFAULT NULL,
  `xi` double(6, 2) NULL DEFAULT NULL,
  `measure` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '度量单位',
  `evaluate` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '评价(营养方面)',
  `ingredient` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '主料(菜品)',
  `excipient` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '辅料(菜品)',
  `practice` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '做法(菜品)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for foodlabel
-- ----------------------------
DROP TABLE IF EXISTS `foodlabel`;
CREATE TABLE `foodlabel`  (
  `label_id` int(5) NOT NULL COMMENT '食物标签(高脂肪)',
  `food_id` int(5) NOT NULL,
  PRIMARY KEY (`label_id`, `food_id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `foodlabel_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foodlabel_ibfk_2` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime(6) NOT NULL COMMENT '创建菜单的日期',
  `account` int(5) NOT NULL COMMENT '所有者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account`(`account`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`account`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menuitem
-- ----------------------------
DROP TABLE IF EXISTS `menuitem`;
CREATE TABLE `menuitem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单项的名称，如鸡爪',
  `menu` int(5) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu`(`menu`) USING BTREE,
  CONSTRAINT `menuitem_ibfk_1` FOREIGN KEY (`menu`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userlabel
-- ----------------------------
DROP TABLE IF EXISTS `userlabel`;
CREATE TABLE `userlabel`  (
  `account_id` int(5) NOT NULL COMMENT '相对于的用户',
  `label_id` int(5) NOT NULL COMMENT '食物标签名（辣，高甜，高脂肪），也可以是口味(甜)，初始化时输入',
  `weight` double(6, 2) NULL DEFAULT NULL COMMENT '食物标签对于用户的权重值（不定期更新）',
  PRIMARY KEY (`account_id`, `label_id`) USING BTREE,
  INDEX `account`(`account_id`) USING BTREE,
  INDEX `label_id`(`label_id`) USING BTREE,
  CONSTRAINT `userlabel_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userlabel_ibfk_2` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
