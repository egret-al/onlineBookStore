/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.24 : Database - bookstore_user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore_user` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore_user`;

/*Table structure for table `sys_account` */

DROP TABLE IF EXISTS `sys_account`;

CREATE TABLE `sys_account` (
  `username` varchar(10) NOT NULL COMMENT '登录账号',
  `password` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `score` int(11) DEFAULT '0' COMMENT '账号积分',
  `create_time` datetime DEFAULT NULL COMMENT '账号创建时间',
  `balance` int(11) DEFAULT '0' COMMENT '账户余额',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_account` */

insert  into `sys_account`(`username`,`password`,`score`,`create_time`,`balance`) values ('1234567890','rdq35AiIigZAOAPYoRkNgA==',629,'2020-09-11 00:00:00',5162),('1234567891','Me3l+bqiM/q1idjDUtlItQ==',846,'2020-09-12 20:45:42',9506),('1234567892','JVs4RXWoNG+0hS0gJar72Q==',2,'2020-12-09 11:11:12',1972),('1234567893','U6pna9cmKyy5FYVuxK7ZeQ==',122,'2020-09-15 16:46:41',11739),('1234567894','zNFvTAD2qREhJyAnMlswCA==',2,'2020-12-09 14:29:31',3972);

/*Table structure for table `sys_address` */

DROP TABLE IF EXISTS `sys_address`;

CREATE TABLE `sys_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `a_id` varchar(10) DEFAULT NULL COMMENT '账号表外键字段',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '签收人姓名',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  KEY `ADDRESS_AID_ACCOUNT` (`a_id`),
  CONSTRAINT `ADDRESS_AID_ACCOUNT` FOREIGN KEY (`a_id`) REFERENCES `sys_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `sys_address` */

insert  into `sys_address`(`id`,`a_id`,`phone`,`receiver_name`,`address`) values (1,'1234567890','17012345678','张三33','重庆市忠县乌杨镇'),(4,'1234567891','17012345678','测试用户','四川省xxx'),(6,'1234567890','17012345678','测试用户','四川省xxx'),(9,'1234567893','17012345678','测试用户','四川省xxx'),(11,'1234567890','17602382966','测试2','重庆市忠县乌杨镇'),(12,'1234567892','17012345678','receiver','四川省xxx'),(13,'1234567894','17602382966','receiver2','四川省德阳市旌阳区xxx');

/*Table structure for table `sys_admin` */

DROP TABLE IF EXISTS `sys_admin`;

CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `username` varchar(6) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `create_time` datetime DEFAULT NULL COMMENT '账号创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_admin` */

insert  into `sys_admin`(`id`,`username`,`password`,`create_time`) values (1,'224375','Th/bRFdrtKs9yFEqdfCZRA==','2020-11-01 10:42:00'),(3,'224376','X2WIqOiuMg1RNNwUWrBeFw==','2020-12-15 22:05:44');

/*Table structure for table `sys_comment` */

DROP TABLE IF EXISTS `sys_comment`;

CREATE TABLE `sys_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_username` varchar(10) DEFAULT NULL COMMENT '账号表的外键字段',
  `b_id` int(11) DEFAULT NULL COMMENT '来自bookstore_books库的图书id，这里也就是被评论的物品',
  `content` varchar(300) DEFAULT NULL COMMENT '评论的内容',
  `create_time` datetime DEFAULT NULL COMMENT '评论的时间',
  PRIMARY KEY (`id`),
  KEY `COMMENT_UID_USER` (`account_username`),
  CONSTRAINT `COMMENT_UID_USER` FOREIGN KEY (`account_username`) REFERENCES `sys_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `sys_comment` */

insert  into `sys_comment`(`id`,`account_username`,`b_id`,`content`,`create_time`) values (1,'1234567890',1,'test sending comment 1','2020-11-18 10:04:56'),(2,'1234567890',1,'test sending comment 2','2020-11-18 13:19:07'),(3,'1234567890',1,'test sending comment 3','2020-11-18 13:19:37'),(4,'1234567890',2,'test sending comment 1','2020-11-18 13:20:50'),(5,'1234567890',2,'test sending comment 2','2020-11-18 13:28:38'),(6,'1234567890',2,'test sending comment 3','2020-11-18 13:33:32'),(7,'1234567890',2,'test sending comment 4','2020-11-18 13:33:38'),(8,'1234567890',2,'test sending comment 5','2020-11-18 13:33:42'),(9,'1234567890',3,'test sending comment 1','2020-11-18 21:37:48'),(10,'1234567890',4,'test sending comment 1','2020-11-18 21:40:48'),(11,'1234567890',4,'test sending comment 2','2020-11-18 21:40:54'),(12,'1234567890',4,'test sending comment 3','2020-11-18 21:40:58'),(13,'1234567890',1,'test sending comment 4','2020-11-18 21:42:53'),(14,'1234567890',1,'test sending comment 5','2020-11-18 21:42:56'),(15,'1234567890',1,'test sending comment 6','2020-11-18 21:43:00'),(16,'1234567890',1,'test sending comment 7','2020-11-18 21:43:05'),(17,'1234567890',1,'test sending comment 8','2020-11-18 21:43:08'),(18,'1234567890',1,'test sending comment 9','2020-11-18 21:43:13'),(19,'1234567890',1,'test sending comment 10','2020-11-18 21:43:17'),(20,'1234567890',1,'test sending comment 11','2020-11-18 21:43:37'),(21,'1234567890',1,'test sending comment 12','2020-11-18 21:43:40'),(22,'1234567890',3,'aaaaa','2020-11-26 21:08:45'),(23,'1234567890',16,'你好1','2020-11-28 17:23:48'),(24,'1234567890',4,'hello1','2020-11-28 17:24:08'),(25,'1234567890',1,'邝邝','2020-12-02 15:35:11'),(26,'1234567890',2,'123','2020-12-10 16:57:22'),(27,'1234567891',3,'CET4: changes in the way of transportation','2020-12-13 10:35:11'),(28,'1234567890',2,'aaa','2020-12-13 10:38:58');

/*Table structure for table `sys_shopping_trolley` */

DROP TABLE IF EXISTS `sys_shopping_trolley`;

CREATE TABLE `sys_shopping_trolley` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `b_id` int(11) NOT NULL COMMENT '图书表的主键冗余字段',
  `a_id` varchar(10) NOT NULL COMMENT '账号表的外键字段',
  `collect_count` int(11) NOT NULL COMMENT '收藏的数量',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  KEY `SHOPPING_TROLLEY_AID_ACCOUNT` (`a_id`),
  CONSTRAINT `SHOPPING_TROLLEY_AID_ACCOUNT` FOREIGN KEY (`a_id`) REFERENCES `sys_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `sys_shopping_trolley` */

insert  into `sys_shopping_trolley`(`id`,`b_id`,`a_id`,`collect_count`,`create_time`) values (9,2,'1234567890',6,'2020-11-26 15:23:05'),(10,11,'1234567890',4,'2020-11-30 11:01:53'),(12,1,'1234567891',13,'2020-12-09 15:59:55'),(13,25,'1234567891',2,'2020-12-09 17:04:04'),(14,21,'1234567891',1,'2020-12-09 17:05:02'),(15,42,'1234567891',4,'2020-12-09 17:05:17'),(16,37,'1234567891',3,'2020-12-09 17:05:21'),(17,42,'1234567892',1,'2020-12-09 17:05:43'),(18,25,'1234567892',3,'2020-12-09 17:05:48'),(19,12,'1234567892',1,'2020-12-09 17:07:03'),(20,22,'1234567892',4,'2020-12-09 17:07:46'),(22,41,'1234567890',3,'2020-12-09 17:18:25'),(23,42,'1234567890',4,'2020-12-10 15:52:52'),(24,25,'1234567890',1,'2020-12-13 18:34:30'),(25,32,'1234567890',1,'2020-12-15 09:49:07');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `a_id` varchar(10) DEFAULT NULL COMMENT '账号表的外键字段',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `birthday` datetime DEFAULT NULL COMMENT '用户生日',
  `sex` char(2) DEFAULT NULL COMMENT '性别',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `last_login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  `default_address_id` int(11) DEFAULT '-1' COMMENT '默认地址',
  PRIMARY KEY (`id`),
  KEY `USER_AID_ACCOUNT` (`a_id`),
  CONSTRAINT `USER_AID_ACCOUNT` FOREIGN KEY (`a_id`) REFERENCES `sys_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`a_id`,`nickname`,`birthday`,`sex`,`phone`,`last_login_time`,`default_address_id`) values (1,'1234567890','test111','2000-01-01 00:00:00','女','17602382966','2020-09-12 20:45:42',6),(2,'1234567891','测试2','2000-01-01 00:00:00','男','17602382966','2020-09-12 20:45:42',4),(3,'1234567893','测试3','2000-09-15 00:00:00','女','17602382966','2020-09-15 16:46:41',9),(4,'1234567892','tom','2020-11-07 00:00:00','女','17602382966','2020-12-09 11:11:12',12),(5,'1234567894','jack','2011-12-09 00:00:00','女','17602382966','2020-12-09 14:29:31',13);

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'increment id',
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime NOT NULL COMMENT 'create datetime',
  `log_modified` datetime NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';

/*Data for the table `undo_log` */

/* Trigger structure for table `sys_address` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `current_is_default` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'skip-grants user'@'skip-grants host' */ /*!50003 TRIGGER `current_is_default` AFTER INSERT ON `sys_address` FOR EACH ROW 
begin
    declare total int;
    set total = (
        select count(id)
        from sys_address
        where a_id = NEW.a_id
    );
    if total = 1 then
        # 当前address就是默认地址，更新user的默认地址
        update sys_user
            set sys_user.default_address_id = NEW.id
        where a_id = NEW.a_id;
    end if;
end */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
