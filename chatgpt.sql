/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云服务器
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 101.43.161.104:3306
 Source Schema         : chatgpt

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 20/04/2023 01:45:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for MenuList
-- ----------------------------
DROP TABLE IF EXISTS `MenuList`;
CREATE TABLE `MenuList`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `menuName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `icon` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `disabled` tinyint(1) NULL DEFAULT 0,
  `parentId` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `requiredRole` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `orderBy` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of MenuList
-- ----------------------------
INSERT INTO `MenuList` VALUES (1, '/welcome', '欢迎回来', 'el-icon-star-off', 0, '', 'user', '0');
INSERT INTO `MenuList` VALUES (2, '/users', '用户管理', 'el-icon-user', 0, '3', 'developer', '');
INSERT INTO `MenuList` VALUES (3, '', '权限管理', 'el-icon-view', 0, '', 'developer', '3');
INSERT INTO `MenuList` VALUES (4, '/chat', '开始聊天', 'el-icon-user', 0, '', 'user', '1');
INSERT INTO `MenuList` VALUES (10, '/roleAdmin', '角色管理', 'el-icon-user', 0, '3', 'admin', '');
INSERT INTO `MenuList` VALUES (15, 'ApiAdmin', 'API管理', 'el-icon-setting', 0, '3', 'admin', '');

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `disabled` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of Role
-- ----------------------------
INSERT INTO `Role` VALUES (1, 'developer', '0');
INSERT INTO `Role` VALUES (2, 'user', '0');
INSERT INTO `Role` VALUES (9, 'admin', '0');

-- ----------------------------
-- Table structure for RoleUrlRelation
-- ----------------------------
DROP TABLE IF EXISTS `RoleUrlRelation`;
CREATE TABLE `RoleUrlRelation`  (
  `Role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of RoleUrlRelation
-- ----------------------------
INSERT INTO `RoleUrlRelation` VALUES ('user', '/chatGPT/chat', 'ChatGPT接口', 'ChatGPT');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/menuList/getMenuList', '根据身份动态获取系统菜单', '系统管理');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/module/getModuleOptions', '获取全部系统模块', '系统管理');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/news/getNewestMessageList', '查看最新消息', '欢迎页面');
INSERT INTO `RoleUrlRelation` VALUES ('admin', '/role/getAllRoles', '根据搜索条件查询角色列表', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('developer', '/roleUrl/getAllApi', '查询全部API接口', '权限管理');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/roleUrl/selectRolesHasThisAuthority', '查询拥有此接口权限的角色', '权限管理');
INSERT INTO `RoleUrlRelation` VALUES ('developer', '/roleUrl/updateRolesHasAuthorityById', '更改拥有此接口权限的角色', '权限管理');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/selectMemberModelRecord/getCurrentAccountID', '查询当前登录账号的ID', '权限管理');
INSERT INTO `RoleUrlRelation` VALUES ('admin', '/userRole/getAllRoles', '获取全部角色列表', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('admin', '/userRole/getRolesById', '根据ID获取当前用户拥有角色', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('admin', '/userRole/updateRolesById', '更新当前ID拥有的角色', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('developer', '/users/getAllUsers', '获取队员列表', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('developer', '/users/insertUser', '插入一条新队员信息', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('developer', '/users/isForbiddenUserById', '禁用或解禁当前选择账号', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('user', '/users/updatePassword', '修改当前登录用户密码', '用户管理');
INSERT INTO `RoleUrlRelation` VALUES ('admin', '/users/updateUserById', '根据用户ID修改信息', '用户管理');

-- ----------------------------
-- Table structure for USERS
-- ----------------------------
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE `USERS`  (
  `PASSWORD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW',
  `ID` int NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ISENABLED` tinyint(1) NULL DEFAULT 1,
  `VOCATION` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DEPARTMENT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOIN_DATE` datetime(6) NULL DEFAULT NULL,
  `QQ` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sumpoints` int NULL DEFAULT NULL,
  `currentMonthPoints` int NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10621 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of USERS
-- ----------------------------
INSERT INTO `USERS` VALUES ('$2a$10$xYB7xqk8y/OHA4L8Yfiy7eVr7mIKCIOR9ry20GS9WqpITBqpzj/LG', 10000, '本能', 1, '', '', '2015-12-14 16:00:00.000000', '', '', NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10142, '沈子杰', 1, NULL, NULL, '2023-04-12 02:22:17.573000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10143, '任孟光', 1, NULL, NULL, '2023-04-12 16:05:49.755000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10144, '王司宇', 1, NULL, NULL, '2023-04-12 16:05:49.755000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10146, '芊芊', 1, NULL, NULL, '2023-04-12 17:28:26.037000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10147, '开封', 1, NULL, NULL, '2023-04-13 19:22:07.389000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10148, '张尤嘉', 1, NULL, NULL, '2023-04-17 18:57:17.398000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10619, '张鹤', 1, NULL, NULL, '2023-04-12 16:19:10.190000', NULL, NULL, NULL, NULL);
INSERT INTO `USERS` VALUES ('$2a$10$GHu6d0akGdTVFkb4qvKuY./N4.SAHCZuGEzooKCbINQhJrnM0hzxW', 10620, '沈子杰翻译', 1, NULL, NULL, '2023-04-19 00:18:07.701000', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for UserRoleRelation
-- ----------------------------
DROP TABLE IF EXISTS `UserRoleRelation`;
CREATE TABLE `UserRoleRelation`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ROLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `USERID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 152 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of UserRoleRelation
-- ----------------------------
INSERT INTO `UserRoleRelation` VALUES (12, 'user,admin,developer', '10000');
INSERT INTO `UserRoleRelation` VALUES (144, 'user', '10142');
INSERT INTO `UserRoleRelation` VALUES (145, 'user', '10143');
INSERT INTO `UserRoleRelation` VALUES (146, 'user', '10144');
INSERT INTO `UserRoleRelation` VALUES (147, 'user', '10619');
INSERT INTO `UserRoleRelation` VALUES (148, 'user', '10146');
INSERT INTO `UserRoleRelation` VALUES (149, 'user', '10147');
INSERT INTO `UserRoleRelation` VALUES (150, 'user', '10148');
INSERT INTO `UserRoleRelation` VALUES (151, 'user', '10620');

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `moduleName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES (1, '擂台分组器');
INSERT INTO `module` VALUES (2, '用户管理');
INSERT INTO `module` VALUES (3, '职业管理');
INSERT INTO `module` VALUES (4, '娱乐游戏');
INSERT INTO `module` VALUES (5, '4V4记录');
INSERT INTO `module` VALUES (6, '计划管理');
INSERT INTO `module` VALUES (7, '赛事类别管理');
INSERT INTO `module` VALUES (8, '擂台分组器');
INSERT INTO `module` VALUES (9, '系统管理');
INSERT INTO `module` VALUES (10, '新闻管理');
INSERT INTO `module` VALUES (11, '队规管理');
INSERT INTO `module` VALUES (12, '积分管理');
INSERT INTO `module` VALUES (13, '权限管理');
INSERT INTO `module` VALUES (14, '欢迎页面');

-- ----------------------------
-- Table structure for requesturl
-- ----------------------------
DROP TABLE IF EXISTS `requesturl`;
CREATE TABLE `requesturl`  (
  `requestUrl` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of requesturl
-- ----------------------------
INSERT INTO `requesturl` VALUES ('/FVF/', '查询分组情况');
INSERT INTO `requesturl` VALUES ('/FVF/checkScore', '获取比分数据，更新参赛选手积分');
INSERT INTO `requesturl` VALUES ('\r\n/FVF/clearAll', '清空分组器');
INSERT INTO `requesturl` VALUES ('/FVF/dg', '分组');
INSERT INTO `requesturl` VALUES ('/vocations/getAllVocations', '获取全部职业');
INSERT INTO `requesturl` VALUES ('/userRole/getAllRoles', '获取全部角色列表');
INSERT INTO `requesturl` VALUES ('/userRole/getRolesById', '根据ID获取当前用户拥有角色');
INSERT INTO `requesturl` VALUES ('\r\n/userRole/updateRolesById', '更新当前ID拥有的角色');
INSERT INTO `requesturl` VALUES ('/menuList/getMenuList', '根据登录账户权限动态获取菜单');
INSERT INTO `requesturl` VALUES ('/users/getAllUsers', '查询全部用户');
INSERT INTO `requesturl` VALUES ('/users/insertUser', '插入一条新队员信息');
INSERT INTO `requesturl` VALUES ('/users/updateUserById', '根据用户ID修改信息');
INSERT INTO `requesturl` VALUES ('/role/getAllRoles', '根据搜索条件查询角色列表');

SET FOREIGN_KEY_CHECKS = 1;
