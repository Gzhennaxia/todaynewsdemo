/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.5.27 : Database - todaynews
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`todaynews` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `todaynews`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `content` varchar(128) DEFAULT NULL,
  `state` int(5) DEFAULT NULL COMMENT '删除位',
  `news_id` int(11) DEFAULT NULL COMMENT '新闻id',
  `uid` int(11) DEFAULT NULL COMMENT '评论者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`created_date`,`content`,`state`,`news_id`,`uid`) values (1,'2018-04-21 09:36:42','测试测试',0,5,2),(2,'2018-04-22 21:09:38','cc',0,7,2);

/*Table structure for table `conversation` */

DROP TABLE IF EXISTS `conversation`;

CREATE TABLE `conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '会话拥有者id',
  `friend_id` int(11) DEFAULT NULL COMMENT '会话对象id',
  `earliest_message_date` datetime DEFAULT NULL COMMENT '最早的消息的时间',
  `state` int(5) DEFAULT NULL COMMENT '删除位',
  `message_count` int(11) DEFAULT NULL COMMENT '该会话包含的私信数',
  `unread_count` int(11) DEFAULT NULL COMMENT '未读个数',
  `latest_message_content` text COMMENT '最近的一条消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `conversation` */

insert  into `conversation`(`id`,`user_id`,`friend_id`,`earliest_message_date`,`state`,`message_count`,`unread_count`,`latest_message_content`) values (15,2,1,'2018-04-22 20:58:11',0,2,0,'黄桑发给朕的私信测试'),(16,1,2,'2018-04-22 20:58:11',0,2,0,'黄桑发给朕的私信测试'),(17,5,2,'2018-04-22 23:42:21',0,1,0,'youge发给朕的私信'),(18,2,5,'2018-04-22 23:42:21',0,1,0,'youge发给朕的私信'),(19,0,2,'2018-04-23 21:39:01',0,2,0,'您好，zhen给您分享的新闻《<a href=/news/6>测试用例2</a>》点了一个赞'),(20,2,0,'2018-04-23 21:39:01',0,2,0,'您好，zhen给您分享的新闻《<a href=/news/6>测试用例2</a>》点了一个赞'),(21,2,3,'2018-04-23 22:05:06',0,1,0,'朕发给gzhennaxia@163.com的私信测试'),(22,3,2,'2018-04-23 22:05:06',0,1,1,'朕发给gzhennaxia@163.com的私信测试'),(23,0,1,'2018-04-23 23:11:31',0,1,0,'您好，zhen给您分享的新闻《<a href=/news/7>测试用例3</a>》点了一个赞'),(24,1,0,'2018-04-23 23:11:31',0,1,1,'您好，zhen给您分享的新闻《<a href=/news/7>测试用例3</a>》点了一个赞'),(25,0,9,'2018-04-24 00:03:40',0,1,0,'您好，zhen给您分享的新闻《<a href=/news/8>让我交代那女的是谁！你做的梦我怎么知道是谁</a>》点了一个赞'),(26,9,0,'2018-04-24 00:03:40',0,1,0,'您好，zhen给您分享的新闻《<a href=/news/8>让我交代那女的是谁！你做的梦我怎么知道是谁</a>》点了一个赞');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '本条记录拥有者id',
  `friend_id` int(11) DEFAULT NULL COMMENT '私信对象id',
  `from_id` int(11) DEFAULT NULL COMMENT '发件人id',
  `to_id` int(11) DEFAULT NULL COMMENT '收件人id',
  `content` text,
  `created_date` datetime DEFAULT NULL,
  `state` int(5) DEFAULT NULL COMMENT '1/2/3-未读/已读/删除',
  `conversation_id` int(11) DEFAULT NULL COMMENT '所属会话id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`id`,`user_id`,`friend_id`,`from_id`,`to_id`,`content`,`created_date`,`state`,`conversation_id`) values (19,2,1,2,1,'朕发给黄桑的私信测试','2018-04-22 20:58:11',2,15),(20,1,2,2,1,'朕发给黄桑的私信测试','2018-04-22 20:58:11',2,16),(21,1,2,1,2,'黄桑发给朕的私信测试','2018-04-22 21:00:28',2,16),(22,2,1,1,2,'黄桑发给朕的私信测试','2018-04-22 21:00:28',2,15),(23,5,2,5,2,'youge发给朕的私信','2018-04-22 23:42:21',2,17),(24,2,5,5,2,'youge发给朕的私信','2018-04-22 23:42:21',2,18),(25,0,2,0,2,'您好，huangsang给您分享的新闻《<a href=/news/5>测试用例</a>》点了一个赞','2018-04-23 21:39:01',2,19),(26,2,0,0,2,'您好，huangsang给您分享的新闻《<a href=/news/5>测试用例</a>》点了一个赞','2018-04-23 21:39:01',2,20),(27,2,3,2,3,'朕发给gzhennaxia@163.com的私信测试','2018-04-23 22:05:06',2,21),(28,3,2,2,3,'朕发给gzhennaxia@163.com的私信测试','2018-04-23 22:05:06',1,22),(29,0,2,0,2,'您好，zhen给您分享的新闻《<a href=/news/6>测试用例2</a>》点了一个赞','2018-04-23 22:20:25',2,19),(30,2,0,0,2,'您好，zhen给您分享的新闻《<a href=/news/6>测试用例2</a>》点了一个赞','2018-04-23 22:20:25',2,20),(31,0,1,0,1,'您好，zhen给您分享的新闻《<a href=/news/7>测试用例3</a>》点了一个赞','2018-04-23 23:11:31',2,23),(32,1,0,0,1,'您好，zhen给您分享的新闻《<a href=/news/7>测试用例3</a>》点了一个赞','2018-04-23 23:11:31',1,24),(33,0,9,0,9,'您好，zhen给您分享的新闻《<a href=/news/8>让我交代那女的是谁！你做的梦我怎么知道是谁</a>》点了一个赞','2018-04-24 00:03:40',2,25),(34,9,0,0,9,'您好，zhen给您分享的新闻《<a href=/news/8>让我交代那女的是谁！你做的梦我怎么知道是谁</a>》点了一个赞','2018-04-24 00:03:40',2,26);

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `image` varchar(128) DEFAULT NULL COMMENT '新闻图片',
  `title` varchar(30) DEFAULT NULL,
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `link` varchar(128) DEFAULT NULL COMMENT '新闻链接',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `state` int(5) DEFAULT '0' COMMENT '删除位',
  `uid` int(11) DEFAULT NULL COMMENT '提供者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `news` */

insert  into `news`(`id`,`create_date`,`image`,`title`,`like_count`,`link`,`comment_count`,`state`,`uid`) values (4,'2018-04-20 20:18:03','/images/upload/2efc4d2e-a093-4cf6-b04d-30f380f309020d98f.jpg','测试用例',NULL,'www.baidu.com',NULL,NULL,NULL),(5,'2018-04-20 20:32:23','/images/upload/d9991d7f-8ee8-445c-9453-7cc89278799a0d98f.jpg','测试用例',5,'https://gzhennaxia.github.io/',1,0,2),(6,'2018-04-20 21:46:34','/images/upload/e5b55e1b-26ed-4d0e-abb4-7dc68d1680254d6.jpg','测试用例2',0,'https://gzhennaxia.github.io/',0,0,2),(7,'2018-04-20 22:26:20','/images/upload/25be2e40-6ffe-4923-9ff1-f6eeda08eaae23sdf.jpg','测试用例3',1,'https://gzhennaxia.github.io/',1,0,1),(8,'2018-04-23 23:23:13','/images/upload/b4b40d7e-9eb9-4649-8562-9e868455544215242972.jpg','让我交代那女的是谁！你做的梦我怎么知道是谁',1,'https://www.toutiao.com/a6546807213770408462/',0,0,9);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `head_url` varchar(128) DEFAULT NULL COMMENT '用户头像',
  `state` int(11) DEFAULT NULL COMMENT '删除位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`head_url`,`state`) values (0,'系统','666666','/images/avatar/avatar00.jpg',0),(1,'huangsang','666666','/images/avatar/avatar07.jpg',0),(2,'zhen','666666','/images/avatar/avatar01.jpg',0),(3,'gzhennaxia@163.com','666666','/images/avatar/avatar03.jpg',0),(5,'youge','666666','/images/avatar/avatar02.jpg',0),(9,'test','666666','/images/avatar/avatar01.jpg',0),(10,'test2','666666','/images/avatar/avatar05.jpg',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
