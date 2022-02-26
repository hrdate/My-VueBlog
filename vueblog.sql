/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : vueblog

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 25/02/2022 13:49:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_article
-- ----------------------------
DROP TABLE IF EXISTS `m_article`;
CREATE TABLE `m_article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章内容',
  `created` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '文章状态',
  `tagId` int NULL DEFAULT NULL COMMENT '文章标签',
  `view_count` int NULL DEFAULT NULL COMMENT '文章浏览量',
  `is_top` tinyint NULL DEFAULT NULL COMMENT '文字置顶',
  `article_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章分类',
  `article_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章封面链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_article
-- ----------------------------
INSERT INTO `m_article` VALUES (1, 1, '生活就像海洋，只有意志坚强的人才能到达彼岸', '内容？？？\n++++\n---\n****', '2022-02-12 15:25:15', 0, 1, 35, 0, '原创', 'https://diloveyu.gitee.io/image-cloud/vueBlog/QuAn_1.ajpg');
INSERT INTO `m_article` VALUES (2, 1, '最值得学习的博客项目eblog', '**推荐阅读：**\r\n\r\n[分享一套SpringBoot开发博客系统源码，以及完整开发文档！速度保存！](https://mp.weixin.qq.com/s/jz6e977xP-OyaAKNjNca8w)\r\n\r\n[Github上最值得学习的100个Java开源项目，涵盖各种技术栈！](https://mp.weixin.qq.com/s/N-U0TaEUXnBFfBsmt_OESQ)\r\n\r\n[2020年最新的常问企业面试题大全以及答案](https://mp.weixin.qq.com/s/lR5LC5GnD2Gs59ecV5R0XA)', '2021-12-14 15:13:49', 0, 1, 30, 0, '转载', 'https://diloveyu.gitee.io/image-cloud/vueBlog/QuAn_2.jpg');
INSERT INTO `m_article` VALUES (3, 1, '关注--', '### 工具获取\r\n\r\n* xshell 6 绿色破解版：关注公众号：JavaCat，回复 xshell 获取\r\n* Navicat 11 简体中文版：关注公众号：JavaCat，回复 navicat 获取\r\n\r\n公众号二维码：\r\n\r\n![JavaCat](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20201020/7fa16a1f957f4cfebe7be1f6675f6f36.png \"JavaCat\")\r\n\r\n直接扫码回复对应关键字\r\n\r\n**推荐阅读：**\r\n\r\n[B站86K播放量，SpringBoot+Vue前后端分离完整入门教程！](https://mp.weixin.qq.com/s/jGEkHTf2X8l-wUenc-PpEw)\r\n\r\n[分享一套SpringBoot开发博客系统源码，以及完整开发文档！速度保存！](https://mp.weixin.qq.com/s/jz6e977xP-OyaAKNjNca8w)\r\n\r\n[Github上最值得学习的100个Java开源项目，涵盖各种技术栈！](https://mp.weixin.qq.com/s/N-U0TaEUXnBFfBsmt_OESQ)\r\n\r\n[2020年最新的常问企业面试题大全以及答案](https://mp.weixin.qq.com/s/lR5LC5GnD2Gs59ecV5R0XA)', '2022-01-04 15:14:25', 1, 2, 10, 0, '原创', 'https://diloveyu.gitee.io/image-cloud/vueBlog/QuAn_3.jpg');
INSERT INTO `m_article` VALUES (7, 1, '你真的会写单例模式吗?', '> 作者：吃桔子的攻城狮 来源：http://www.tekbroaden.com/singleton-java.html\n\n\n单例模式可能是代码最少的模式了，但是少不一定意味着简单，想要用好、用对单例模式，还真得费一番脑筋。本文对 Java 中常见的单例模式写法做了一个总结，如有错漏之处，恳请读者指正。\n\n饿汉法\n===\n\n顾名思义，饿汉法就是在第一次引用该类的时候就创建对象实例，而不管实际是否需要创建。代码如下：\n\n```\npublic class Singleton {  \n    private static Singleton = new Singleton();\n    private Singleton() {}\n    public static getSignleton(){\n        return singleton;\n    }\n}\n\n```\n\n这样做的好处是编写简单，但是无法做到延迟创建对象。但是我们很多时候都希望对象可以尽可能地延迟加载，从而减小负载，所以就需要下面的懒汉法：\n', '2021-09-15 15:14:51', 0, 2, 25, 0, '转载', 'https://diloveyu.gitee.io/image-cloud/vueBlog/articleCover_2-10.jpg');
INSERT INTO `m_article` VALUES (9, 1, '真正理解Mysql的四种隔离级别@', '### 什么是事务  \n\n> 事务是应用程序中一系列严密的操作，所有操作必须成功完成，否则在每个操作中所作的所有更改都会被撤消。也就是事务具有原子性，一个事务中的一系列的操作要么全部成功，要么一个都不做。\n> \n> 事务的结束有两种，当事务中的所以步骤全部成功执行时，事务提交。如果其中一个步骤失败，将发生回滚操作，撤消撤消之前到事务开始时的所以操作。\n\n**事务的 ACID**\n\n事务具有四个特征：原子性（ Atomicity ）、一致性（ Consistency ）、隔离性（ Isolation ）和持续性（ Durability ）。这四个特性简称为 ACID 特性。\n\n> 1 、原子性。事务是数据库的逻辑工作单位，事务中包含的各操作要么都做，要么都不做\n> \n> 2 、一致性。事 务执行的结果必须是使数据库从一个一致性状态变到另一个一致性状态。因此当数据库只包含成功事务提交的结果时，就说数据库处于一致性状态。如果数据库系统 运行中发生故障，有些事务尚未完成就被迫中断，这些未完成事务对数据库所做的修改有一部分已写入物理数据库，这时数据库就处于一种不正确的状态，或者说是 不一致的状态。', '2022-02-12 15:15:09', 0, 1, 10, 0, '原创', 'https://diloveyu.gitee.io/image-cloud/vueBlog/about.jpg');
INSERT INTO `m_article` VALUES (10, 1, '博客项目eblog讲解视频上线', 'ok，再回到我们的eblog项目，源码、文档、视频我都开源出来了。来些基本操作：github上给个star，B站视频给个三连支持咧。\n\neblog源码：https://github.com/MarkerHub/eblog\n\n点击这里：[10+篇完整开发文档](https://mp.weixin.qq.com/mp/homepage?__biz=MzIwODkzOTc1MQ==&hid=1&sn=8e512316c3dfe140e636d0c996951166)\n\n![](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200508/c290d945b7d24c79b172759bdb5b94e0.png)\n\n视频讲解：（记得关注我噢！）\n\nhttps://www.bilibili.com/video/BV1ri4y1x71A\n\n![](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200508/983b5abc1c934360a1a1362347a275f7.png)\n\n项目其实还很多bug的，哈哈，我还需要进行二次迭代，到时候再发迭代文档出来。\n\n关注下我的B站，作为一个自媒体的自由职业者，没有什么比涨粉更让我开心的了，嘻嘻。\n\n近期即将推出的视频教程：\n\n1. 搭建脚手架，前后端分离首秀\n2. Shiro入门到精通教程\n3. SpringBoot2.2.6最新入门教程', '2021-12-22 15:15:11', 0, 1, 95, 0, '翻译', 'https://diloveyu.gitee.io/image-cloud/vueBlog/message.jpg');
INSERT INTO `m_article` VALUES (15, 1, 'test标题', 'test\n\n+++', '2022-02-19 18:31:54', 0, 1, 135, 1, '原创', NULL);

-- ----------------------------
-- Table structure for m_bloginfo
-- ----------------------------
DROP TABLE IF EXISTS `m_bloginfo`;
CREATE TABLE `m_bloginfo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `views_count` bigint NULL DEFAULT NULL COMMENT '网站浏览量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user_count` int NULL DEFAULT NULL COMMENT '用户数量',
  `article_count` int NULL DEFAULT NULL COMMENT '文章数量',
  `tag_count` int NULL DEFAULT NULL COMMENT '标签数量',
  `message_count` int NULL DEFAULT NULL COMMENT '留言数量',
  `comments_count` int NULL DEFAULT NULL COMMENT '评论数量',
  `config` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_bloginfo
-- ----------------------------
INSERT INTO `m_bloginfo` VALUES (1, 524, '2021-08-09 19:37:30', '2022-01-23 23:09:50', 3, 7, 3, 6, 11, '{\"gitee\":\"https://gitee.com/diloveyu\",\"github\":\"https://github.com/hrdate\",\"isChatRoom\":1,\"isCommentReview\":0,\"isEmailNotice\":1,\"isMessageReview\":0,\"isMusicPlayer\":1,\"isReward\":1,\"qq\":\"535523596\",\"socialLoginList\":[\"qq\"],\"socialUrlList\":[\"qq\",\"github\",\"gitee\"],\"touristAvatar\":\"https://static.talkxj.com/photos/0bca52afdb2b9998132355d716390c9f.png\",\"userAvatar\":\"https://static.talkxj.com/config/2cd793c8744199053323546875655f32.jpg\",\"websiteAuthor\":\"di\",\"websiteAvatar\":\"https://static.talkxj.com/config/43a07ac1ca201143f7b938d0791124fc.png\",\"websiteCreateTime\":\"October 6,2021\",\"websiteIntro\":\"预备失业人员之只学半年Java的蒟蒻。\",\"websiteName\":\"diloveyu\",\"websiteNotice\":\"本站还在测试中，请前往后台管理->系统管理->网站管理处修改信息\",\"websiteRecordNo\":\"备案号test\",\"websocketUrl\":\"ws://127.0.0.1:8080/websocket\",\"weiXinQRCode\":\"https://static.talkxj.com/photos/4f767ef84e55ab9ad42b2d20e51deca1.png\"}');

-- ----------------------------
-- Table structure for m_comments
-- ----------------------------
DROP TABLE IF EXISTS `m_comments`;
CREATE TABLE `m_comments`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `articleId` int NULL DEFAULT NULL COMMENT '博客文章id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `created` datetime NULL DEFAULT NULL COMMENT '留言时间',
  `is_review` tinyint NULL DEFAULT NULL COMMENT '是否审核',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父评论id',
  `reply_userId` int NULL DEFAULT NULL COMMENT '回复用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_comments
-- ----------------------------
INSERT INTO `m_comments` VALUES (1, 1, 1, 'test_for_test', '2022-01-26 21:09:48', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (2, 7, 2, 'test_for_test', '2022-01-27 22:50:49', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (3, 15, 2, '在线测试', '2022-01-01 13:51:51', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (4, NULL, 2, '友链评论测试', '2022-01-28 23:36:24', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (5, NULL, 2, 'test_for_friendLink', '2022-01-29 10:05:02', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (6, 10, 2, 'test_for_123', '2022-01-29 19:01:02', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (7, 10, 2, '测试1', '2022-01-29 19:46:36', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (8, 3, 2, 'listComment——test', '2022-01-29 19:49:24', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (9, 2, 2, '回复测试21:16', '2022-02-11 21:16:36', 1, 5, 2);
INSERT INTO `m_comments` VALUES (10, 15, 2, '测试留言2月11日21：27', '2022-02-11 21:27:12', 1, NULL, NULL);
INSERT INTO `m_comments` VALUES (11, 15, 2, '测试留言回复1', '2022-02-11 21:53:11', 1, 14, 2);

-- ----------------------------
-- Table structure for m_friend
-- ----------------------------
DROP TABLE IF EXISTS `m_friend`;
CREATE TABLE `m_friend`  (
  `linkId` int NOT NULL AUTO_INCREMENT COMMENT '友链id',
  `linkName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '友链名',
  `linkUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '友链URL',
  `linkInfo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '友链信息',
  `avatar` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '友链头像',
  `created` datetime NULL DEFAULT NULL COMMENT '友链创建时间',
  PRIMARY KEY (`linkId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_friend
-- ----------------------------
INSERT INTO `m_friend` VALUES (1, 'testMan', 'https://www.baidu.com', 'testInfo', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '2021-12-01 00:00:00');
INSERT INTO `m_friend` VALUES (2, 'QuAn_12', 'https://gitee.com/diloveyu/vueblog/blob/Main/vueblog-vue/src/assets/img/QuAn_1.jpg', 'test2', 'https://diloveyu.gitee.io/image-cloud/vueBlog/QuAn_1.jpg', '2021-10-06 17:15:48');
INSERT INTO `m_friend` VALUES (3, 'QuAn_2', 'https://www.duitang.com/blog/?id=1249921689', 'test3', 'https://diloveyu.gitee.io/image-cloud/vueBlog/QuAn_2.jpg', '2021-12-23 18:30:00');
INSERT INTO `m_friend` VALUES (4, 'hyy', 'https://www.hrd_hyy.com', 'loveyu', 'https://static.talkxj.com/photos/0bca52afdb2b9998132355d716390c9f.png', '2022-01-24 17:21:12');
INSERT INTO `m_friend` VALUES (9, 'test', 'https://www2.zhihu.com/hot', '测试', 'https://diloveyu.gitee.io/image-cloud/vj.jpg', '2022-01-26 20:57:03');

-- ----------------------------
-- Table structure for m_message
-- ----------------------------
DROP TABLE IF EXISTS `m_message`;
CREATE TABLE `m_message`  (
  `id` int NOT NULL COMMENT '留言id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `created` datetime NULL DEFAULT NULL COMMENT '留言创建时间',
  `avatar` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户头像',
  `time` int NULL DEFAULT NULL COMMENT '弹幕速度',
  `is_review` tinyint NULL DEFAULT NULL COMMENT '是否已审核',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ip来源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_message
-- ----------------------------
INSERT INTO `m_message` VALUES (1, 1, '丶di', 'test', '2021-12-10 12:00:00', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 7, 1, NULL, NULL);
INSERT INTO `m_message` VALUES (2, 2, 'test', 'test2', '2021-12-10 20:36:48', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 8, 1, NULL, NULL);
INSERT INTO `m_message` VALUES (4, 3, 'test', 'test4', '2022-01-16 16:18:16', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 8, 0, NULL, NULL);
INSERT INTO `m_message` VALUES (5, 3, 'test', 'test5', '2022-01-16 16:18:35', 'https://static.talkxj.com/photos/0bca52afdb2b9998132355d716390c9f.png', 9, 1, NULL, NULL);
INSERT INTO `m_message` VALUES (6, 3, 'hyy', 'love', '2022-01-24 12:46:42', 'https://static.talkxj.com/config/2cd793c8744199053323546875655f32.jpg', 8, 1, NULL, NULL);

-- ----------------------------
-- Table structure for m_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `m_operation_log`;
CREATE TABLE `m_operation_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返回数据',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1062 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_operation_log
-- ----------------------------
INSERT INTO `m_operation_log` VALUES (1, '友链模块', '新增或修改', '/admin/links', 'com.minzheng.blog.controller.FriendLinkController.saveOrUpdateFriendLink', '保存或修改友链', '[{\"linkAddress\":\"https://www.talkxj.com\",\"linkAvatar\":\"https://static.talkxj.com/photos/b553f564f81a80dc338695acb1b475d2.jpg\",\"linkIntro\":\"往事不随风\",\"linkName\":\"风丶宇的个人博客\"}]', 'POST', '{\"code\":20000,\"flag\":true,\"message\":\"操作成功\"}', 1, '管理员', '127.0.0.1', '', '2022-01-18 00:26:46', NULL);
INSERT INTO `m_operation_log` VALUES (2, '文章模块', '新增或修改', '/admin/articles', 'com.minzheng.blog.controller.ArticleController.saveOrUpdateArticle', '添加或修改文章', '[{\"articleContent\":\"恭喜你成功运行博客！\",\"articleCover\":\"https://static.talkxj.com/articles/74ddc4cd4b5abc76c6088a83bbd8c8a5.jpg\",\"articleTitle\":\"测试文章\",\"categoryName\":\"测试分类\",\"isTop\":0,\"originalUrl\":\"\",\"status\":1,\"tagNameList\":[\"测试标签\"],\"type\":1}]', 'POST', '{\"code\":20000,\"flag\":true,\"message\":\"操作成功\"}', 1, '管理员', '127.0.0.1', '', '2022-01-18 00:29:02', NULL);

-- ----------------------------
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS `m_role`;
CREATE TABLE `m_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  `role_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色描述',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用  0否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_role
-- ----------------------------
INSERT INTO `m_role` VALUES (1, '管理员', 'admin', 0, '2021-03-22 14:10:21', '2021-10-05 15:55:58');
INSERT INTO `m_role` VALUES (2, '用户', 'user', 0, '2021-03-22 14:25:25', '2021-08-24 11:47:08');
INSERT INTO `m_role` VALUES (3, '测试', 'test', 0, '2021-03-22 14:42:23', '2021-08-24 16:01:19');

-- ----------------------------
-- Table structure for m_tag
-- ----------------------------
DROP TABLE IF EXISTS `m_tag`;
CREATE TABLE `m_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'tag的id',
  `tagName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'tag的名字',
  `created` datetime NULL DEFAULT NULL COMMENT 'tag的创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_tag
-- ----------------------------
INSERT INTO `m_tag` VALUES (1, '学习', '2021-12-01 18:41:12');
INSERT INTO `m_tag` VALUES (2, '生活', '2021-12-08 12:00:00');
INSERT INTO `m_tag` VALUES (3, 'JAVA', '2022-01-24 16:48:42');

-- ----------------------------
-- Table structure for m_test
-- ----------------------------
DROP TABLE IF EXISTS `m_test`;
CREATE TABLE `m_test`  (
  `id` int NULL DEFAULT NULL COMMENT '测试id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试名'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_test
-- ----------------------------

-- ----------------------------
-- Table structure for m_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `m_unique_view`;
CREATE TABLE `m_unique_view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '时间',
  `views_count` int NOT NULL COMMENT '访问量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_unique_view
-- ----------------------------

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `status` int NULL DEFAULT NULL COMMENT '用户状态',
  `created` datetime NULL DEFAULT NULL COMMENT '用户创建时间',
  `lastLogin` datetime NULL DEFAULT NULL COMMENT '用户最近登录时间',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户自我介绍',
  `webSite` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户网址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `UK_USERNAME`(`userName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES (1, 'admin', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '535523596@qq.com', '21232f297a57a5a743894a0e4a801fc3', 0, '2020-04-20 10:44:01', '2021-12-06 11:45:12', '自由在高出', 'https://blog.csdn.net/hrd535523596?spm=1001.2101.3001.5343');
INSERT INTO `m_user` VALUES (2, 'test', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 'test@qq.com', '21232f297a57a5a743894a0e4a801fc3', 0, '2021-12-06 12:38:54', '2021-12-06 12:38:57', '测试一下哈', 'https://www.cnblogs.com/hrdate/');
INSERT INTO `m_user` VALUES (3, '用户ddtime', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '2698640956@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '2021-12-16 15:13:51', '2021-12-19 20:19:57', '123456', 'https://www.baidu');

-- ----------------------------
-- Table structure for m_user_role
-- ----------------------------
DROP TABLE IF EXISTS `m_user_role`;
CREATE TABLE `m_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_user_role
-- ----------------------------
INSERT INTO `m_user_role` VALUES (1, 1, 1);
INSERT INTO `m_user_role` VALUES (2, 2, 2);
INSERT INTO `m_user_role` VALUES (3, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
