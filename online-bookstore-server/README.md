时间：2020年9月10日22:52:09

数据库设计：

```sql
use bookstore_user;

# 账户表
create table sys_account
(
    `username` varchar(10) primary key comment '登录账号',
    `password` varchar(100) comment '登录密码',
    `score` int default 0 comment '账号积分',
    `create_time` datetime comment '账号创建时间'
) engine=innodb default character set utf8;
insert into sys_account(username, password, score, create_time)
values ('1234567890', '123456', 0, '2020-09-11 00:00:00');

select *
from sys_account;


# 用户信息表
create table sys_user
(
    `id` int primary key auto_increment comment '主键自增长字段',
    `a_id` varchar(10) comment '账号表的外键字段',
    `nickname` varchar(20) comment '昵称',
    `birthday` datetime comment '用户生日',
    `sex` char(2) comment '性别',
    `phone` char(11) comment '手机号',
    `last_login_time` datetime comment '上一次登录时间',
    constraint USER_AID_ACCOUNT foreign key (a_id) references sys_account(username)
) engine=innodb default character set utf8;
insert into sys_user(a_id, nickname, birthday, sex, phone, last_login_time)
values('1234567890', '测试用户', '2000-01-01 00:00:00', '女', '13000000000', null);


# 用户地址表
create table sys_address(
    `id` int primary key auto_increment comment '主键自增长字段',
    `a_id` varchar(10) comment '账号表的外键字段',
    `address` varchar(100) comment '地址',
    constraint ADDRESS_AID_ACCOUNT foreign key (a_id) references sys_account(username)
) engine=innodb default character set utf8;

# 评论表
create table sys_comment
(
    id int primary key auto_increment,
    u_id int comment '用户信息表的外键字段，这里也就是发起评论的人',
    b_id int comment '来自bookstore_books库的图书id，这里也就是被评论的物品',
    content varchar(300) comment '评论的内容',
    create_time datetime comment '评论的时间',
    constraint COMMENT_UID_USER foreign key (u_id) references sys_user(id)
) engine=innodb default character set utf8;

desc sys_account;


use bookstore_books;
# 类别库
create table sys_book_type
(
    id int primary key auto_increment comment '主键自增长',
    type varchar(10) comment '分类',
    supplement varchar(30) comment '补充说明'
) engine=innodb default character set utf8;

# 图书表
create table sys_book
(
    id int primary key auto_increment comment '主键自增长字段',
    book_name varchar(50) comment '图书名称',
    isbn varchar(20) comment 'ISBN',
    author varchar(20) comment '书本作者',
    publisher varchar(20) comment '出版社',
    price int comment '图书价格',
    create_time datetime comment '上架时间',
    t_id int comment '分类表的外键字段',
    constraint BOOK_TID_TYPE foreign key (t_id) references sys_book_type(id)
) engine=innodb default character set utf8;

# 静态资源库
create table sys_book_resource
(
    id int primary key auto_increment comment '主键自增长字段',
    b_id int comment '图书表的外键字段',
    resource_url varchar(200) comment '对应图书的资源路径',
    supplement varchar(50) comment '补充说明',
    create_time datetime comment '上传时间',
    constraint BOOK_RESOURCE_BID_BOOK foreign key (b_id) references sys_book(id)
) engine=innodb default character set utf8;

# 库存表
create table sys_book_storage
(
    id int primary key auto_increment comment '主键自增长字段',
    b_id int comment '图书表的外键字段',
    last_add_time datetime comment '最后一次添加时间',
    residue_count int default 0 comment '剩余数量',
    constraint STORAGE_BID_BOOK foreign key (b_id) references sys_book(id)
) engine=innodb default character set utf8;


use bookstore_order;
# 订单表
create table sys_order
(
    serial_number varchar(30) primary key comment '订单编号',
    book_id int comment '来自bookstore_books库的图书表的id',
    username_id varchar(10) comment '来自bookstore_user库的账号表登录账号',
    order_content varchar(100) comment '订单内容',
    product_count int default 1 comment '购买商品数量，默认为1',
    whole_price int comment '订单总价',
    obtain_score int comment '获得的积分',
    create_time datetime comment '订单创建时间',
    payment_time datetime comment '订单付款时间',
    delivery_time datetime comment '发货时间',
    end_time datetime comment '订单结束时间',
    order_payment_status tinyint default 0 comment '订单状态，默认为0代表未支付，1为成功，-1失败'
) engine=innodb default character set utf8;
```