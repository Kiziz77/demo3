/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.26 : Database - a_db_springboot_vue
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`a_db_springboot_vue` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `a_db_springboot_vue`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `is_default` int NOT NULL DEFAULT '0' COMMENT '是否默认地址[ 1 是/ 0 否]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='收货地址';

/*Data for the table `address` */

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户ID',
  `comment_val` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '评论内容',
  `moment_id` bigint DEFAULT NULL COMMENT '关联主体ID（例:帖子ID/活动ID）',
  `comment_type` int DEFAULT NULL COMMENT '评论类型(例:1.帖子,2.活动)',
  `parent_id` int DEFAULT NULL COMMENT '直接父级ID(顶级留言ID;子级留言ID)',
  `root_comment_id` int DEFAULT NULL COMMENT '顶级评论ID(区分顶级留言和子留言)',
  `status` int DEFAULT NULL COMMENT '业务状态：1 评论 2 回复',
  `reply_comment` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回复详情',
  `image_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '留言图片',
  `created_by` int DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` int DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除(0未删除;1已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='评论留言表';

/*Data for the table `comments` */

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='配置文件';

/*Data for the table `config` */

insert  into `config`(`id`,`name`,`value`) values 
(1,'图1','http://localhost:9999/upload/1741437187688.png'),
(2,'图2','http://localhost:9999/upload/1741437220080.png'),
(4,'图3','http://localhost:9999/upload/1741437231010.png'),
(5,'图4','http://localhost:9999/upload/1741437279757.png');

/*Table structure for table `like_collect` */

DROP TABLE IF EXISTS `like_collect`;

CREATE TABLE `like_collect` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户ID',
  `moment_id` int DEFAULT NULL COMMENT '关联主体ID（数据id）',
  `sign_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '关联模块（表名 or ''标识''）',
  `is_like` int NOT NULL DEFAULT '0' COMMENT '是否点赞 0：否 ；1：是',
  `is_collect` int NOT NULL DEFAULT '0' COMMENT '是否收藏 0：否 ；1：是',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0未删除;1已删除)',
  `full_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由',
  `pictures_field` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '展示图片字段',
  `title_field` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '展示标题字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='点赞收藏表';

/*Data for the table `like_collect` */

insert  into `like_collect`(`id`,`user_id`,`moment_id`,`sign_name`,`is_like`,`is_collect`,`deleted`,`full_path`,`pictures_field`,`title_field`) values 
(49,3,1,'products',0,1,0,'/index/productsDetail?id=1','image_url','products_name'),
(50,1,2,'products',0,0,0,'/index/productsDetail?id=2','image_url','products_name'),
(51,1,1,'products',0,1,0,'/index/productsDetail?id=1','image_url','products_name');

/*Table structure for table `login_info` */

DROP TABLE IF EXISTS `login_info`;

CREATE TABLE `login_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录ip',
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录浏览器',
  `referer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源URL',
  `login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `session_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '会话id',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录地点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='登录日志';

/*Data for the table `login_info` */

insert  into `login_info`(`id`,`user_name`,`user_ip`,`user_agent`,`referer`,`login_time`,`session_id`,`location`) values 
(1,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-09 19:45:38','A718BA9F0BC05BA55B1FAFA5B64ABD7F',NULL),
(2,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-10 19:45:43','F78778E07753FC1571184EA34906E5F9',NULL),
(3,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-11 19:53:02','CC05AF4BFAF477717FE995C56EF0AD98',NULL),
(4,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-11 19:53:26','F630BBCEC85875D8480FA19B15587983',NULL),
(5,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-12 15:55:56','CB95BD8F92BA2A9F86101B9C3515AB79',NULL),
(6,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-12 17:53:49','7F2C0661A41B216E072E4845BAEC8A5D',NULL),
(7,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-14 10:54:09','1CE7C18F785EDDC164E9A0B5D7F4FF6A',NULL),
(8,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-14 15:37:36','A5CB4C58151B35CE912B587EB491B335',NULL),
(9,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 16:50:36','8940971E3F8A0A02A75400E7017B2965',NULL),
(10,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 20:17:22','CCEEC013A57FE4AF12ED701B52D5C80B',NULL),
(11,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 21:35:55','5AEAD1524454CF65E8F110874D4ED5C3',NULL),
(12,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 21:49:48','A35DEEDDF1330FAFD47E53B7B19250D2',NULL),
(13,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 21:49:50','792CCC8B156AED8DD621004E7FCF0493',NULL),
(14,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 21:50:21','475A513148CD78DA1B848F920105BE63',NULL),
(15,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-15 21:52:54','AA4521DEC63469A80ED523EF81F0E9BE',NULL),
(16,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-18 15:21:17','C481541259050F3D2484E03DD322A415',NULL),
(17,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-02-18 15:49:38','805ACC0956545181D7A98F0FB45B5615',NULL),
(18,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 20:17:43','AB5B0DA03E66FC1D86B9853EED105A96',NULL),
(19,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 20:56:10','DA0BB532220E6834AE444173DA1BC877',NULL),
(20,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:11:45','17D3DC1F71A20931EA16BB4951F33354',NULL),
(21,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:20:32','46C35FE0F4DAEE9D6CD57D815FFDC601',NULL),
(22,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:20:48','DDF597A6F42E830F3EAFBF416D4E5C3E',NULL),
(23,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:23:12','0B2860850ACE68E72FFA348F08440F0F',NULL),
(24,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:23:29','F9E0D4CE811584C2F0FEF266CE63A312',NULL),
(25,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:27:03','ADE2E37C8F6FF883B1D5D614CEDA9B4E',NULL),
(26,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:28:33','A18AE661FA93580A510D062895A15B98',NULL),
(27,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:32:54','A5BFF4C7819416C53C42026C036AB655',NULL),
(28,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-20 21:33:02','65E1747026DA0EE2F4BAEAF87207ACAC',NULL),
(29,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 09:20:31','C1C79D79109563F94B97C8D35D9BD67D',NULL),
(30,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 10:24:00','19587CA99445F7A61355C56A55B59850',NULL),
(31,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 11:27:09','EB7274B9ABB9EE8030769CCABEB5C316',NULL),
(32,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 12:55:54','F0A781B3EEDE626B27C2659BD0C818CB',NULL),
(33,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 13:57:56','71FC98E0DB03C5E773F618A901CF18F5',NULL),
(34,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 15:00:54','95EF496CAC34EF3924B9529EE339B7E5',NULL),
(35,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 15:27:50','A6510504390EC9DCA60F6B5837602102',NULL),
(36,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 16:10:43','6C3C7B9CE52EDFC4BECF7F4234D7A044',NULL),
(37,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 16:12:48','241148618BDB7E8D5670FA86FCA60311',NULL),
(38,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 16:13:50','408A494B5655D5360F14EB938DF807C8',NULL),
(39,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 17:19:06','B8972E2B869F585C9E8719091D34FA9C',NULL),
(40,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 18:00:23','E4CF109CFE2F81AE6BB4CC212EDDD362',NULL),
(41,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:04:38','1B89CF4DA06BF416B0A141C54640E406',NULL),
(42,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:31:12','279F40CD9739027C546B426F1AA59DA3',NULL),
(43,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:32:12','2B332962E1BBB67B4CD43426069B95EA',NULL),
(44,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:34:11','115FCBE93B538CEB291DC046A04E886F',NULL),
(45,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:34:31','163658011F39D15124369642BA056EC5',NULL),
(46,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:44:03','A1D474D5F9618C3A28DDA8F6EAE3D8BE',NULL),
(47,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:44:27','B42549943F109D25A28CD46120810002',NULL),
(48,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:45:48','F4F250835948FBD503BFA23A3F0A68DF',NULL),
(49,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:46:24','1D5CAFDD32228BFC307313A0E3280CB6',NULL),
(50,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:46:36','69EAB18D720DC5B60519A3FC2A2ED6AA',NULL),
(51,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:47:08','B9E492B8280495CEA04D9554AF09E845',NULL),
(52,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:48:31','C4BD7F65FB289938312A025A6D92A06F',NULL),
(53,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:48:47','254F66B7DE5851482AF91130A4DA2269',NULL),
(54,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-02-21 19:51:09','6B48F92083CD1F8E38592B39F11CD7FC',NULL),
(55,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-03-01 14:46:23','97AFED68ACB47BA631E31EE5648C5759',NULL),
(56,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-03-05 15:25:55','A83F78637A5B65342A5308FC25482ACB',NULL),
(57,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0','http://localhost:7777/','2025-03-05 20:34:02','66FD1FBD16B31898A1881736735D1D31',NULL),
(58,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-03-07 09:57:21','54E70E78024CF47F6C0599CC7A1A7DFF',NULL),
(59,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-03-08 20:08:15','366BEC117817A1E0F379792B0E3106A4',NULL),
(60,'admin','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36','http://localhost:7777/','2025-03-08 20:47:49','E5E79D32A626986F4F140595F2AB2596',NULL);

/*Table structure for table `managers` */

DROP TABLE IF EXISTS `managers`;

CREATE TABLE `managers` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='管理员';

/*Data for the table `managers` */

insert  into `managers`(`id`,`account_number`,`password`,`real_name`,`avatar`,`phone`,`email`,`create_time`,`role`) values 
(1,'admin','admin','admin','http://localhost:9999/upload/1739186178886.PNG','18256568989','admin@qq.com','2025-01-22 10:29:00','管理员'),
(2,'kd','kd','杜兰特','https://b0.bdstatic.com/092d4259a03e725399027b952e652fd1.jpg@h_1280','15256569696','kd@126.com','2025-02-06 18:10:41','管理员');

/*Table structure for table `notices` */

DROP TABLE IF EXISTS `notices`;

CREATE TABLE `notices` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `pictures` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='公告';

/*Data for the table `notices` */

insert  into `notices`(`id`,`title`,`content`,`pictures`,`create_time`,`update_time`) values 
(1,'系统维护通知','<p>66666尊敬的各位用户，系统将于2024年3月20日凌晨2:00至5:00进行维护升级，期间将暂停服务。请提前做好相关准备，给您带来不便，敬请谅解。<br><img src=\"https://picsum.photos/600/400?random=1\" alt=\"维护图片\" data-href=\"\" style=\"\"/></p>','https://picsum.photos/300/200?random=1','2025-02-10 19:00:26','2025-03-01 15:16:12'),
(2,'新功能上线公告','<p>我们很高兴地宣布，新功能“数据分析报告”已正式上线！您可以在系统中查看详细的数据分析结果，帮助您更好地决策。欢迎体验并提出宝贵意见。6666666666666<br><img src=\"https://picsum.photos/600/400?random=2\" alt=\"新功能图片\" data-href=\"\" style=\"\"/></p>','https://picsum.photos/500/400','2025-02-10 19:00:26','2025-02-12 16:37:12'),
(3,'春节放假安排','根据国家法定节假日安排，公司将于2024年2月9日至2月15日放假，2月16日恢复正常工作。祝大家春节快乐，阖家幸福！<br><img src=\"https://picsum.photos/600/400?random=3\" alt=\"春节图片\" style=\"max-width:100%;border-radius:8px;margin:10px 0;\">','https://picsum.photos/300/200?random=3','2025-02-10 19:00:26','2025-02-10 19:15:22'),
(4,'安全升级提醒','为了提升系统安全性，我们将在2024年3月25日进行安全升级。请各位用户及时修改密码，并确保账户信息的安全。如有问题，请联系客服。<br><img src=\"https://picsum.photos/600/400?random=4\" alt=\"安全升级图片\" style=\"max-width:100%;border-radius:8px;margin:10px 0;\">','https://picsum.photos/300/200?random=4','2025-02-10 19:00:26','2025-02-10 19:15:22'),
(5,'用户反馈奖励计划','<p>为了感谢广大用户的支持，我们推出了用户反馈奖励计划。凡是在2024年4月1日前提交有效反馈的用户，将有机会获得精美礼品一份！777777777777777777777777<br><img src=\"https://picsum.photos/600/400?random=5\" alt=\"反馈奖励图片\" data-href=\"\" style=\"\"/></p>','https://picsum.photos/300/200?random=5','2025-02-10 19:00:26','2025-02-12 16:42:10'),
(6,'关于系统优化的通知','我们已对系统进行了全面优化，提升了响应速度和稳定性。如果您在使用过程中遇到任何问题，请及时联系技术支持团队。感谢您的理解与支持！<br><img src=\"https://picsum.photos/600/400?random=6\" alt=\"系统优化图片\" style=\"max-width:100%;border-radius:8px;margin:10px 0;\">','https://picsum.photos/300/200?random=6','2025-02-10 19:00:26','2025-02-10 19:15:22'),
(7,'dd','<p><img src=\"http://localhost:9999/upload/1740125733045.jpg\" alt=\"1740125733045.jpg\" data-href=\"http://localhost:9999/upload/1740125733045.jpg\" style=\"\"/></p>','http://localhost:9999/upload/1740125749404.jpg','2025-02-21 16:15:51','2025-02-21 16:15:51');

/*Table structure for table `product_order` */

DROP TABLE IF EXISTS `product_order`;

CREATE TABLE `product_order` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户ID',
  `moment_id` int NOT NULL COMMENT '关联主体ID（数据id）',
  `sign_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联模块（表名 or ''标识''）',
  `full_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由',
  `pictures` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名称',
  `buy_number` int DEFAULT NULL COMMENT '购买数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `discount_price` decimal(10,2) DEFAULT NULL COMMENT '折扣单价',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价格',
  `discount_total_price` decimal(10,2) DEFAULT NULL COMMENT '折扣总价',
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单编号',
  `buy_datetime` datetime DEFAULT NULL COMMENT '购买时间',
  `order_type` int NOT NULL DEFAULT '1' COMMENT '类型  0：订单，1：购物车',
  `order_status` int DEFAULT '0' COMMENT '订单状态 0:待支付，1：已支付，已发货，已完成，已退款，已取消',
  `payment_method` int DEFAULT NULL COMMENT '支付方式 0：微信，1：支付宝',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `logistics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '物流',
  `creat_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='商品订单表';

/*Data for the table `product_order` */

insert  into `product_order`(`id`,`user_id`,`moment_id`,`sign_name`,`full_path`,`pictures`,`title`,`buy_number`,`price`,`discount_price`,`total_price`,`discount_total_price`,`order_number`,`buy_datetime`,`order_type`,`order_status`,`payment_method`,`address`,`phone`,`consignee`,`remark`,`logistics`,`creat_time`) values 
(105,1,2,'products','/index/productsDetail?id=2','http://localhost:9999/upload/1741178128223.jpg','智能洗衣机',1,3999.99,NULL,3999.99,NULL,NULL,NULL,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `products_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `brand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品品牌',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格，保留两位小数',
  `stock_quantity` int NOT NULL DEFAULT '0' COMMENT '库存数量，默认为0',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品信息表';

/*Data for the table `products` */

insert  into `products`(`id`,`products_name`,`brand`,`description`,`price`,`stock_quantity`,`image_url`,`create_time`,`update_time`) values 
(1,'精品保温杯','supor','<p>精品保温杯，你值得拥有</p>',20.00,30,'http://localhost:9999/upload/1741178073659.jpg','2025-03-05 20:34:49',NULL),
(2,'智能洗衣机','海尔','<p>海尔智能洗衣机，就是牛</p>',3999.99,50,'http://localhost:9999/upload/1741178128223.jpg','2025-03-05 20:35:39',NULL);

/*Table structure for table `token` */

DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `table_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表名',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色',
  `token` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '凭证',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expirated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='登录凭证表';

/*Data for the table `token` */

insert  into `token`(`id`,`user_id`,`user_name`,`table_name`,`role`,`token`,`create_time`,`expirated_time`) values 
(1,1,'admin','managers','管理员','553559498229420032','2025-02-06 20:38:17','2025-03-08 21:47:49'),
(2,1,'zmm','users','用户','553567201253265408','2025-02-09 18:03:17','2025-03-08 22:18:26'),
(3,4,'2','users','用户','543763256616030208','2025-02-09 20:01:03','2025-02-09 21:01:03'),
(4,3,'zs','users','用户','552468801015386112','2025-02-11 16:44:05','2025-03-05 21:33:47');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `balance` double(10,2) DEFAULT '0.00' COMMENT '余额',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `gender` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户';

/*Data for the table `users` */

insert  into `users`(`id`,`user_name`,`password`,`avatar`,`balance`,`email`,`phone`,`gender`,`create_time`,`update_time`) values 
(1,'zmm','123456','http://localhost:9999/upload/1739169413219.PNG',0.00,'zmm@qq.com','18256456888','女','2025-02-09 17:59:51','2025-02-11 16:43:19'),
(3,'zs','123456',NULL,0.00,'1@qq.com','18256568989',NULL,'2025-02-09 18:31:12','2025-02-11 16:43:59'),
(4,'2','2','http://localhost:9999/upload/1739102474687.PNG',0.00,'2@qq.com','18254545656','男','2025-02-09 18:31:38','2025-02-11 16:43:16');

DROP TABLE IF EXISTS `drug_reservation`;
CREATE TABLE `drug_reservation` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `customer_name` varchar(255)   COMMENT '客户姓名' ,
    `phone` varchar(255)   COMMENT '联系电话' ,
    `drug_id` int  COMMENT '药品ID' ,
    `reserve_quantity` int  COMMENT '预约数量' ,
    `reserve_time` datetime  COMMENT '预约时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品预约';
DROP TABLE IF EXISTS `supplier_info`;
CREATE TABLE `supplier_info` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `supplier_name` varchar(255)   COMMENT '供应商名称' ,
    `contact_person` varchar(255)   COMMENT '联系人' ,
    `phone` varchar(255)   COMMENT '联系电话' ,
    `address` varchar(255)   COMMENT '地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商信息';
DROP TABLE IF EXISTS `drug_info`;
CREATE TABLE `drug_info` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `drug_name` varchar(255)   COMMENT '药品名称' ,
    `specification` varchar(255)   COMMENT '规格' ,
    `manufacturer` varchar(255)   COMMENT '生产商' ,
    `approval_number` varchar(255)   COMMENT '批准文号' ,
    `unit_price` int  COMMENT '单价'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品信息';
DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE `customer_order` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `customer_name` varchar(255)   COMMENT '客户姓名' ,
    `phone` varchar(255)   COMMENT '联系电话' ,
    `drug_id` int  COMMENT '药品ID' ,
    `quantity` int  COMMENT '购买数量' ,
    `order_time` datetime  COMMENT '订单时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购买订单';
DROP TABLE IF EXISTS `drug_display`;
CREATE TABLE `drug_display` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `drug_id` int  COMMENT '药品ID' ,
    `drug_image` varchar(255)   COMMENT '药品图片' ,
    `display_status` varchar(255)   COMMENT '展示状态' ,
    `unit_price` int  COMMENT '单价'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品展示';
DROP TABLE IF EXISTS `sales_record`;
CREATE TABLE `sales_record` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `inventory_id` int  COMMENT '库存ID' ,
    `sales_quantity` int  COMMENT '销售数量' ,
    `sales_time` datetime  COMMENT '销售时间' ,
    `customer_name` varchar(255)   COMMENT '客户名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售记录';
DROP TABLE IF EXISTS `purchase_record`;
CREATE TABLE `purchase_record` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `supplier_id` int  COMMENT '供应商ID' ,
    `drug_id` int  COMMENT '药品ID' ,
    `purchase_quantity` int  COMMENT '采购数量' ,
    `purchase_time` datetime  COMMENT '采购时间' ,
    `purchase_id` int  COMMENT '采购记录ID' ,
    `purchase_unit_price` int  COMMENT '采购单价'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购记录';
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
    `id` bigint PRIMARY KEY auto_increment COMMENT '主键' ,
    `drug_id` int  COMMENT '药品ID' ,
    `batch_number` varchar(255)   COMMENT '批次号' ,
    `storage_time` datetime  COMMENT '入库时间' ,
    `expiry_date` date  COMMENT '有效期至' ,
    `quantity` int  COMMENT '当前数量' ,
    `purchase_id` int  COMMENT '采购记录ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存信息';
