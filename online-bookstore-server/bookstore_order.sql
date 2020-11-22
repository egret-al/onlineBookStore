/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.24 : Database - bookstore_order
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore_order` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore_order`;

/*Table structure for table `sys_order` */

DROP TABLE IF EXISTS `sys_order`;

CREATE TABLE `sys_order` (
  `serial_number` varchar(30) NOT NULL COMMENT '订单编号',
  `book_id` int(11) DEFAULT NULL COMMENT '来自bookstore_books库的图书表的id',
  `username_id` varchar(10) DEFAULT NULL COMMENT '来自bookstore_user库的账号表登录账号',
  `order_content` varchar(100) DEFAULT NULL COMMENT '订单内容',
  `product_count` int(11) DEFAULT '1' COMMENT '购买商品数量，默认为1',
  `whole_price` int(11) DEFAULT NULL COMMENT '订单总价',
  `obtain_score` int(11) DEFAULT NULL COMMENT '获得的积分',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `payment_time` datetime DEFAULT NULL COMMENT '订单付款时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单结束时间',
  `order_payment_status` tinyint(4) DEFAULT '0' COMMENT '订单状态，默认为0代表未支付，1为成功，-1失败',
  `use_score` tinyint(4) DEFAULT '0' COMMENT '是否使用积分，默认为0不使用，1为使用',
  `book_name` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '接收人',
  `address` varchar(100) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`serial_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_order` */

insert  into `sys_order`(`serial_number`,`book_id`,`username_id`,`order_content`,`product_count`,`whole_price`,`obtain_score`,`create_time`,`payment_time`,`delivery_time`,`end_time`,`order_payment_status`,`use_score`,`book_name`,`phone`,`receiver_name`,`address`) values ('0005729f-ead4-4562-8523-2c2b72',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-21 11:13:40','2020-11-21 11:14:44','2020-11-21 11:14:45','2020-11-21 11:14:45',1,0,'算法导论','17012345678','张三3','四川省德阳市xxxxxxx'),('4204a452-43c8-4f45-9a94-e6bd62',4,'1234567893','下单了1本《Spring Boot+Vue全栈开发实战》',1,NULL,NULL,'2020-11-21 10:40:55',NULL,NULL,NULL,-1,0,'Spring Boot+Vue全栈开发实战','17012345678','测试用户','四川省xxx'),('4af3932b-df32-41c9-a7de-3346f6',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-20 22:38:27','2020-11-20 22:38:36','2020-11-20 22:38:37','2020-11-20 22:38:37',1,0,'算法导论',NULL,NULL,NULL),('6a27eacd-fa3c-486d-8f27-1dc12f',2,'1234567890','下单了1本《Spring Cloud Alibaba 微服务原理与实战》',1,NULL,NULL,'2020-11-20 22:43:08',NULL,NULL,NULL,-1,0,'Spring Cloud Alibaba 微服务原理与实战','17012345678','张三33','重庆市忠县xxxxxxx'),('7e0f2090-9dad-4dea-acd4-3029c1',3,'1234567890','下单了1本《深入理解Java虚拟机》',1,79,7,'2020-11-21 11:02:48','2020-11-21 11:02:59','2020-11-21 11:03:00','2020-11-21 11:03:00',1,0,'深入理解Java虚拟机','17012345678','张三3','四川省德阳市xxxxxxx'),('7e98c38e-ec01-4f99-8d4b-c6a55b',4,'1234567893','下单了2本《Spring Boot+Vue全栈开发实战》',2,138,13,'2020-11-21 10:40:36','2020-11-21 10:40:40','2020-11-21 10:40:41','2020-11-21 10:40:41',1,1,'Spring Boot+Vue全栈开发实战','17012345678','测试用户','四川省xxx'),('af6287e3-679d-4812-97c1-34e2fc',5,'1234567890','下单了1本《深入浅出Vue.js》',1,NULL,NULL,'2020-11-21 10:41:53',NULL,NULL,NULL,-1,0,'深入浅出Vue.js','17012345678','张三3','四川省德阳市xxxxxxx'),('d23ef891-8dee-4c6a-ac71-143943',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-20 22:44:34','2020-11-20 22:44:36','2020-11-20 22:44:37','2020-11-20 22:44:37',1,0,'算法导论','17012345678','张三3','四川省德阳市xxxxxxx'),('fe5db7d6-6537-4a67-a15f-2d66fe',1,'1234567890','下单了3本《算法导论》',3,267,26,'2020-11-20 22:43:43','2020-11-20 22:43:46','2020-11-20 22:43:46','2020-11-20 22:43:46',1,1,'算法导论','17012345678','张三33','重庆市忠县xxxxxxx');

/*Table structure for table `sys_order_success` */

DROP TABLE IF EXISTS `sys_order_success`;

CREATE TABLE `sys_order_success` (
  `serial_number` varchar(30) NOT NULL COMMENT '订单编号',
  `book_id` int(11) DEFAULT NULL COMMENT '来自bookstore_books库的图书表的id',
  `username_id` varchar(10) DEFAULT NULL COMMENT '来自bookstore_user库的账号表登录账号',
  `order_content` varchar(100) DEFAULT NULL COMMENT '订单内容',
  `product_count` int(11) DEFAULT '1' COMMENT '购买商品数量，默认为1',
  `whole_price` int(11) DEFAULT NULL COMMENT '订单总价',
  `obtain_score` int(11) DEFAULT NULL COMMENT '获得的积分',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `payment_time` datetime DEFAULT NULL COMMENT '订单付款时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单结束时间',
  `order_payment_status` tinyint(4) DEFAULT '0' COMMENT '订单状态，默认为0代表未支付，1为成功，-1失败',
  `use_score` tinyint(4) DEFAULT '0' COMMENT '是否使用积分，默认为0不使用，1为使用',
  `book_name` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '接收人',
  `address` varchar(100) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`serial_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_order_success` */

insert  into `sys_order_success`(`serial_number`,`book_id`,`username_id`,`order_content`,`product_count`,`whole_price`,`obtain_score`,`create_time`,`payment_time`,`delivery_time`,`end_time`,`order_payment_status`,`use_score`,`book_name`,`phone`,`receiver_name`,`address`) values ('0005729f-ead4-4562-8523-2c2b72',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-21 11:13:40','2020-11-21 11:14:44','2020-11-21 11:14:45','2020-11-21 11:14:45',1,0,'算法导论','17012345678','张三3','四川省德阳市xxxxxxx'),('4af3932b-df32-41c9-a7de-3346f6',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-20 22:38:27','2020-11-20 22:38:36','2020-11-20 22:38:37','2020-11-20 22:38:37',1,0,'算法导论',NULL,NULL,NULL),('7e0f2090-9dad-4dea-acd4-3029c1',3,'1234567890','下单了1本《深入理解Java虚拟机》',1,79,7,'2020-11-21 11:02:48','2020-11-21 11:02:59','2020-11-21 11:03:00','2020-11-21 11:03:00',1,0,'深入理解Java虚拟机','17012345678','张三3','四川省德阳市xxxxxxx'),('7e98c38e-ec01-4f99-8d4b-c6a55b',4,'1234567893','下单了2本《Spring Boot+Vue全栈开发实战》',2,138,13,'2020-11-21 10:40:36','2020-11-21 10:40:40','2020-11-21 10:40:41','2020-11-21 10:40:41',1,1,'Spring Boot+Vue全栈开发实战','17012345678','测试用户','四川省xxx'),('d23ef891-8dee-4c6a-ac71-143943',1,'1234567890','下单了1本《算法导论》',1,89,8,'2020-11-20 22:44:34','2020-11-20 22:44:36','2020-11-20 22:44:37','2020-11-20 22:44:37',1,0,'算法导论','17012345678','张三3','四川省德阳市xxxxxxx'),('fe5db7d6-6537-4a67-a15f-2d66fe',1,'1234567890','下单了3本《算法导论》',3,267,26,'2020-11-20 22:43:43','2020-11-20 22:43:46','2020-11-20 22:43:46','2020-11-20 22:43:46',1,1,'算法导论','17012345678','张三33','重庆市忠县xxxxxxx');

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

/* Trigger structure for table `sys_order` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_order_status_insert_bk` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'skip-grants user'@'skip-grants host' */ /*!50003 TRIGGER `update_order_status_insert_bk` AFTER UPDATE ON `sys_order` FOR EACH ROW 
begin
    if NEW.order_payment_status = 1 then
        insert into sys_order_success(serial_number, book_id, username_id, order_content, product_count, whole_price,
                                      obtain_score, create_time, payment_time, delivery_time, end_time,
                                      order_payment_status,
                                      use_score, book_name, phone, receiver_name, address)
        values (NEW.serial_number, NEW.book_id, NEW.username_id, NEW.order_content, NEW.product_count, NEW.whole_price,
                NEW.obtain_score, NEW.create_time, NEW.payment_time, NEW.delivery_time, NEW.end_time,
                NEW.order_payment_status, NEW.use_score, NEW.book_name, NEW.phone, NEW.receiver_name, NEW.address);
    end if;
end */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
