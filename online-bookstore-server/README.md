时间：2020年9月10日22:52:09

数据库设计：

```sql
CREATE TABLE `sys_user`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
    `a_id`            varchar(10) DEFAULT NULL COMMENT '账号表的外键字段',
    `nickname`        varchar(20) DEFAULT NULL COMMENT '昵称',
    `birthday`        datetime    DEFAULT NULL COMMENT '用户生日',
    `sex`             char(2)     DEFAULT NULL COMMENT '性别',
    `phone`           char(11)    DEFAULT NULL COMMENT '手机号',
    `last_login_time` datetime    DEFAULT NULL COMMENT '上一次登录时间',
    PRIMARY KEY (`id`),
    KEY `USER_AID_ACCOUNT` (`a_id`),
    CONSTRAINT `USER_AID_ACCOUNT` FOREIGN KEY (`a_id`) REFERENCES `sys_account` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_account`
(
    `username`    varchar(10) NOT NULL COMMENT '登录账号',
    `password`    varchar(100) DEFAULT NULL COMMENT '登录密码',
    `score`       int(11)      DEFAULT '0' COMMENT '账号积分',
    `create_time` datetime     DEFAULT NULL COMMENT '账号创建时间',
    `balance`     int(11)      DEFAULT '0' COMMENT '账户余额',
    PRIMARY KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_address`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
    `a_id`    varchar(10)  DEFAULT NULL COMMENT '账号表的外键字段',
    `address` varchar(100) DEFAULT NULL COMMENT '地址',
    PRIMARY KEY (`id`),
    KEY `ADDRESS_AID_ACCOUNT` (`a_id`),
    CONSTRAINT `ADDRESS_AID_ACCOUNT` FOREIGN KEY (`a_id`) REFERENCES `sys_account` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_comment`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `u_id`        int(11)      DEFAULT NULL COMMENT '用户信息表的外键字段，这里也就是发起评论的人',
    `b_id`        int(11)      DEFAULT NULL COMMENT '来自bookstore_books库的图书id，这里也就是被评论的物品',
    `content`     varchar(300) DEFAULT NULL COMMENT '评论的内容',
    `create_time` datetime     DEFAULT NULL COMMENT '评论的时间',
    PRIMARY KEY (`id`),
    KEY `COMMENT_UID_USER` (`u_id`),
    CONSTRAINT `COMMENT_UID_USER` FOREIGN KEY (`u_id`) REFERENCES `sys_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
  
  CREATE TABLE `sys_book`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
    `book_name`   varchar(50) DEFAULT NULL COMMENT '图书名称',
    `isbn`        char(13)    DEFAULT NULL,
    `author`      varchar(20) DEFAULT NULL COMMENT '书本作者',
    `publisher`   varchar(20) DEFAULT NULL COMMENT '出版社',
    `price`       int(11)     DEFAULT NULL COMMENT '图书价格',
    `create_time` datetime    DEFAULT NULL COMMENT '上架时间',
    `t_id`        int(11)     DEFAULT NULL COMMENT '分类表的外键字段',
    PRIMARY KEY (`id`),
    KEY `BOOK_TID_TYPE` (`t_id`),
    CONSTRAINT `BOOK_TID_TYPE` FOREIGN KEY (`t_id`) REFERENCES `sys_book_type` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_book_resource`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
    `b_id`         int(11)      DEFAULT NULL COMMENT '图书表的外键字段',
    `resource_url` varchar(200) DEFAULT NULL COMMENT '对应图书的资源路径',
    `symbol`       varchar(20)  DEFAULT NULL COMMENT '标识，用于区分资源类型',
    `supplement`   varchar(50)  DEFAULT NULL COMMENT '补充说明',
    `create_time`  datetime     DEFAULT NULL COMMENT '上传时间',
    PRIMARY KEY (`id`),
    KEY `BOOK_RESOURCE_BID_BOOK` (`b_id`),
    CONSTRAINT `BOOK_RESOURCE_BID_BOOK` FOREIGN KEY (`b_id`) REFERENCES `sys_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_book_storage`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
    `b_id`          int(11)  DEFAULT NULL COMMENT '图书表的外键字段',
    `last_add_time` datetime DEFAULT NULL COMMENT '最后一次添加时间',
    `residue_count` int(11)  DEFAULT '0' COMMENT '剩余数量',
    PRIMARY KEY (`id`),
    KEY `STORAGE_BID_BOOK` (`b_id`),
    CONSTRAINT `STORAGE_BID_BOOK` FOREIGN KEY (`b_id`) REFERENCES `sys_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_book_type`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
    `type`       varchar(10) DEFAULT NULL COMMENT '分类',
    `supplement` varchar(30) DEFAULT NULL COMMENT '补充说明',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
  
CREATE TABLE `sys_order`
(
    `serial_number`        varchar(30) NOT NULL COMMENT '订单编号',
    `book_id`              int(11)      DEFAULT NULL COMMENT '来自bookstore_books库的图书表的id',
    `username_id`          varchar(10)  DEFAULT NULL COMMENT '来自bookstore_user库的账号表登录账号',
    `order_content`        varchar(100) DEFAULT NULL COMMENT '订单内容',
    `product_count`        int(11)      DEFAULT '1' COMMENT '购买商品数量，默认为1',
    `whole_price`          int(11)      DEFAULT NULL COMMENT '订单总价',
    `obtain_score`         int(11)      DEFAULT NULL COMMENT '获得的积分',
    `create_time`          datetime     DEFAULT NULL COMMENT '订单创建时间',
    `payment_time`         datetime     DEFAULT NULL COMMENT '订单付款时间',
    `delivery_time`        datetime     DEFAULT NULL COMMENT '发货时间',
    `end_time`             datetime     DEFAULT NULL COMMENT '订单结束时间',
    `order_payment_status` tinyint(4)   DEFAULT '0' COMMENT '订单状态，默认为0代表未支付，1为成功，-1失败',
    PRIMARY KEY (`serial_number`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```