/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.24 : Database - bookstore_books
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore_books` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore_books`;

/*Table structure for table `sys_book` */

DROP TABLE IF EXISTS `sys_book`;

CREATE TABLE `sys_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `book_name` varchar(50) DEFAULT NULL COMMENT '图书名称',
  `isbn` char(13) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL COMMENT '书本作者',
  `publisher` varchar(20) DEFAULT NULL COMMENT '出版社',
  `price` int(11) DEFAULT NULL COMMENT '图书价格',
  `create_time` datetime DEFAULT NULL COMMENT '上架时间',
  `t_id` int(11) DEFAULT NULL COMMENT '分类表的外键字段',
  `main_cover` varchar(200) DEFAULT NULL COMMENT '图书主封面',
  `description` text COMMENT '描述信息',
  PRIMARY KEY (`id`),
  KEY `BOOK_TID_TYPE` (`t_id`),
  CONSTRAINT `BOOK_TID_TYPE` FOREIGN KEY (`t_id`) REFERENCES `sys_book_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `sys_book` */

insert  into `sys_book`(`id`,`book_name`,`isbn`,`author`,`publisher`,`price`,`create_time`,`t_id`,`main_cover`,`description`) values (1,'算法导论','9787111407010','Thomas H.Cormen','机械工业出版社',89,'2020-10-01 15:35:00',1,'https://img.alicdn.com/imgextra/i3/2451699564/TB2NCz5sVXXXXXhXFXXXXXXXXXX_!!2451699564.jpg_430x430q90.jpg','在有关算法的书中，有一些叙述非常严谨，但不够全面；另一些涉及了大量的题材，但又缺乏严谨性。本书将严谨性和全面性融为一体，深入讨论各类算法，并着力使这些算法的设计和分析能为各个层次的读者接受。全书各章自成体系，可以作为独立的学习单元；算法以英语和伪代码的形式描述，具备初步程序设计经验的人就能看懂；说明和解释力求浅显易懂，不失深度和数学严谨性。\r\n全书选材经典、内容丰富、结构合理、逻辑清晰，对本科生的数据结构课程和研究生的算法课程都是非常实用的教材，在IT专业人员的职业生涯中，本书也是一本案头必备的参考书或工程实践手册。\r\n第3版的主要变化：\r\n·新增了van Emde Boas树和多线程算法，并且将矩阵基础移至附录。\r\n·修订了递归式（现在称为“分治策略”）那一章的内容，更广泛地覆盖分治法。\r\n·移除两章很少讲授的内容：二项堆和排序网络。\r\n·修订了动态规划和贪心算法相关内容。\r\n·流网络相关材料现在基于边上的全部流。\r\n·由于关于矩阵基础和Strassen算法的材料移到了其他章，矩阵运算这一章的内容所占篇幅更小。\r\n·修改了对Knuth-Morris-Pratt字符串匹配算法的讨论。\r\n·新增100道练习和28道思考题，还更新并补充了参考文献。'),(2,'Spring Cloud Alibaba 微服务原理与实战','9787121388248','谭锋','电子工业出版社',106,'2020-10-01 15:35:00',1,'https://img.alicdn.com/imgextra/i3/288902762/O1CN01Bznidn1WH2XUXELCk_!!0-item_pic.jpg_430x430q90.jpg','本书针对Spring Cloud Alibba生态下的技术组件从应用到原理进行全面的分析，涉及的技术组件包括分布式服务治理Dubbo、服务配置和服务注册中心Nacos、分布式限流与熔断Sentinel、分布式消息通信RocketMQ、分布式事务Seata及微服务网关Spring Cloud Gateway。由于Spring Cloud中所有的技术组件都是基于Spring Boot微服务框架来集成的，所以对于Spring Boot的核心原理也做了比较详细的分析。\r\n\r\n本书中涉及的所有技术组件，笔者都采用“场景→需求→解决方案→应用→原理”高效技术学习模型进行设计，以便让读者知其然且知其所以然。在“原理”部分，笔者采用大量的源码及图形的方式来进行分析，帮助读者达到对技术组件深度学习和理解的目标。'),(3,'深入理解Java虚拟机','9787111421900','周志明','机械工业出版社',79,'2020-10-01 15:35:00',1,'https://img.alicdn.com/imgextra/i4/2451699564/O1CN01giaKdB2KWMYQj6XOJ_!!2451699564.jpg_430x430q90.jpg','书的第2版出版于2013年，撰写时是基于早期版本的JDK 7。经过将近十年的时间，今天JDK版本已经发展到了JDK 12及预览版的JDK 13，出现了许多激烈的变革，也涌现了不少令人欣喜的新变化、新风潮。因此笔者在撰写本书第3版时，希望能把这些新的变化融合到已有的知识框架上。全书一共分为五个部分：走近Java、自动内存管理、虚拟机执行子系统、程序编译与代码优化、高效并发。各个部分之间基本上是互相独立的，没有必然的前后依赖，读者可以从任何一个感兴趣的专题开始阅读，但是每个部分中的各个章节间则有先后顺序。同时，在前言部分列出了每章主要更新的内容，以便阅读过第2版的读者可以快速定位获取到新的知识。'),(4,'Spring Boot+Vue全栈开发实战','9787302517979','王松','清华大学出版社',69,'2020-10-01 15:35:00',1,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01urG1AV1vSMcEV5nWM_!!2649526171.jpg_430x430q90.jpg','本书以项目实战为主体，循序渐进地介绍了Spring Boot 2.0在Web应用开发方面的各项技能。第1章由零开始引导读者快速搭建Spring Boot开发环境，对之后Spring Boot的探险之旅奠定基础。第2章、第3章、第10章和第13章介绍Spring Boot数据访问应用，整合众多流行技术Spring Boot集成Druid、Spring Data JPA和MyBatis，快速访问MySQL和Mongo DB数据库。第4~6章重点介绍Spring Boot集成Thymeleaf模板引擎、事务使用以及拦截器和监听器的应用。第7~9章主要介绍Spring Boot使用Redis缓存和Quartz定时器、集成Log4J日志框架和发送Email邮件。第11、12章主要介绍Spring Boot集成ActiveMQ和异步调用、全局异常使用。第14章和第15章主要介绍Spring Boot应用监控和应用安全Security。第16章和第17章介绍Spring boot微服务在Zookeeper注册和Dubbo的使用、多环境配置和使用以及在Tomcat上的部署应用。第18章主要探索Spring Boot背后的原理和执行流程。为帮助读者快速掌握Spring Boot，编者还录制了与本书内容相关的教学视频，读者下载后即可观看学习。'),(5,'深入浅出Vue.js','9787115509055','刘博文','人民邮电出版社',79,'2020-10-01 15:35:00',1,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01jqAEBz2KWMUVxLUj9_!!2451699564.jpg_430x430q90.jpg','本书从源码层面分析了Vue.js。首先，简要介绍了Vue.js；然后详细讲解了其内部核心技术“变化侦测”，这里带领大家从0到1实现一个简单的“变化侦测”系统；接着详细介绍了虚拟DOM技术，其中包括虚拟DOM的原理及其patching算法；紧接着详细讨论了模板编译技术，其中包括模板解析器的实现原理、优化器的原理以及代码生成器的原理；*后详细介绍了其整体架构以及提供给我们使用的各种API的内部原理，同时还介绍了生命周期、错误处理、指令系统与模板过滤器等功能的原理。 本书适合前端开发人员阅读。\r\n\r\n'),(6,'Node.js+Webpack开发实战','9787302555957','夏磊','清华大学出版社',69,'2020-09-28 11:22:00',1,'https://img.alicdn.com/imgextra/i3/2200707119547/O1CN01VcpAIK2KOZr49daIf_!!0-item_pic.jpg_430x430q90.jpg','\"Node.js是一个基于Chrome V8引擎的JavaScript运行环境，它用于构建高速、可伸缩的网络应用程序，为前端开发提供了新的机遇。为了让前端开发者 有效地使用Node.js进行开发，作者结合自己的开发经验编著了本书，全书提供了丰富的示例代码，详细讲述和演示了如何将所学的知识应用于实际的开发中。\r\n    本书分为三部分共21章， 部分Node.js基础：Node.js概述，搭建Node.js开发环境，Node.js编程基础；第二部分后端的Node.js：Express框架，Koa框架，MongoDB数据库，MySQL数据库，ORM框架Sequelize，微博系统实战项目，高性能内存型数据库Redis，前端的发展现状；第三部分前端的Node.js：前端发展状况，Webpack基础，Webpack常用配置，Webpack构建Vue应用，Webpack构建React应用，服务端渲染技术和同构应用的开发，Webpack构建传统多页面Web应用，Webpack性能优化，Webpack自定义Loader的编写，Webpack自定义Plugin的编写。\r\n    本书适合Node.js+Webpack前端开发工程师作为自学参考书，也适合高等院校和培训学校相关专业的师生作为教学参考书。'),(7,'深入浅出Node','9787115335500','朴灵','人民邮电出版社',69,'2020-09-28 11:23:00',1,'https://img.alicdn.com/imgextra/i4/2130152348/O1CN01QntL051TDQXXkunxf_!!0-item_pic.jpg_430x430q90.jpg','《深入浅出Node.js》从不同的视角介绍了Node内在的特点和结构。由首章Node介绍为索引，涉及Node的各个方面，主要内容包含模块机制的揭示、异步I/O实现原理的展现、异步编程的探讨、内存控制的介绍、二进制数据Buffer的细节、Node中的网络编程基础、Node中的Web开发、进程间的消息传递、Node测试以及通过Node构建产品需要的注意事项。最后的附录介绍了Node的安装、调试、编码规范和NPM仓库等事宜。 《深入浅出Node.js》适合想深入了解Node的人员阅读。\r\n\r\n'),(8,'SpringCloud微服务架构实战','9787121382864','陈韶健','电子工业出版社',99,'2020-09-28 11:24:00',1,'https://img.alicdn.com/imgextra/i2/381191231/O1CN01il5ylE1Kxq9OvgdUr_!!0-item_pic.jpg_430x430q90.jpg','本书以Spring Cloud为主导，以电商平台为实例，从服务架构设计的角度，对架构设计、程序开发、运维部署三个层面进行了详细的阐述。本书不仅详细介绍了如何使用Spring Cloud工具套件进行微服务应用的开发，还介绍了如何结合Consul、Docker、Kubernets和Jenkins等的使用方法，将开发的微服务应用以一种可扩展的方式在云端发布。通过对本书的系统学习，读者可快速将所掌握的知识应用于实际工作中，提高自身的职业竞争力。本书的读者对象为广大的Java开发者、系统架构师和系统运维人员。本书适合使用过Spring开源框架或具有一定Spring框架基础知识的读者阅读。'),(9,'RocketMQ技术内幕','9787111614210','丁威 周继锋','机械工业出版社',69,'2020-09-28 11:29:00',1,'https://img.alicdn.com/imgextra/i2/2397078731/O1CN01EqHHTp2EMqXQN7Ijy_!!0-item_pic.jpg_430x430q90.jpg','本书由RocketMQ社区早期的布道者和技术专家撰写，Apache RocketMQ创始人/Linux OpenMessaging创始人兼主席/Alibaba Messaging开源技术负责人冯嘉的高度评价并作序推荐。\r\n\r\n源码角度，本书对RocketMQ的核心技术架构，以及消息发送、消息存储、消息消费、消息过滤、顺序消息、主从同步(HA)、事务消息等主要功能模块的实现原理进行了深入分析，同时展示了源码阅读的相关技巧；应用层面，本书总结了大量RocketMQ的使用技巧。通过本书，读者将深入理解消息中间件和底层网络通讯机制的核心知识点。'),(10,'C++ Primer 英文版 ','9787121200380','Stanley B. Lippman','电子工业出版社',128,'2020-09-28 11:30:00',1,'https://img.alicdn.com/imgextra/i1/1932014659/O1CN011kHrjx7e93iBpUN_!!0-item_pic.jpg_430x430q90.jpg','　　新标准C++11发布，距上一版本已10年；《C++Primer英文版（第5版）》是持续更新的全球C++读本。\r\n　　经过前四个版本积累，第5版的体例堪称完美。\r\n　　这一版本作者历时3年完成，极力避免在原版上升级，而是将C++11的新特性真正融入各章节；更将所有代码示例用C++11的简化写法完成，而不是仅单独增加内容。'),(11,'数学分析华东师大 第五版 上册+下册','9787506762021','于莉萍 李智成','中国医药科技出版社',42,'2020-09-28 11:34:00',2,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01tOpC1a2KWMXsR0iZZ_!!2451699564.jpg_430x430q90.jpg','数学分析(第五版)(上册)  \r\n\r\n本书是“十二五”普通高等教育本科国jia*级规划教材。内容包括实数集与函数、数列极限、函数极限、函数的连续性、导数和微分、\r\n\r\n微分中值定理及其应用、 实数的完备性、不定积分、定积分、定积分的应用、反常积分，附录为微积分学简史、实数理论和不定积分表。\r\n本次修订是在第四版的基础上对一些内容进行适当调整，使该书逻辑性更合理些，并适当补充数字资源。第五版仍旧保持前四版“内容\r\n\r\n选取适当，深入浅出，易教易学，可读性强”的特点。本书可作为高等学校数学和其它相关专业的教材使用。\r\n\r\n数学分析(第五版)(下册)  \r\n\r\n本书是“十二五”普通高等教育本科国jia*级规划教材，普通高等教育十一五国jia*级规划教材和面向21世纪课程教材。内容包括数项级数\r\n\r\n、函数列与函数项级数、幂级数、傅里叶级数、多元函数的极限与连续、多元函数微分学、隐函数定理及其应用、含参量积分、\r\n\r\n曲线积分、重积分、曲面积分、向量函数的微分学等。本次修订是在第四版的基础上对一些内容进行适当调整，\r\n\r\n使教材逻辑性更合理，并适当补充数字资源。第五版仍旧保持前四版“内容选取适当，深入浅出，易教易学，可读性强”的特点。\r\n\r\n本书可作为高等学校数学和其它相关专业的教材使用。'),(12,'高等代数 第五版 北大五版','9787040379105','北京大学数学系前代数小组','高等教育出版社',26,'2020-09-28 11:34:00',2,'https://img.alicdn.com/imgextra/i2/2451699564/O1CN011ntHvJ2KWMWxa6yd8_!!0-item_pic.jpg_430x430q90.jpg','本书是第五版，基本上保持了第四版的内容，增加了几个应用例题，改写了矩阵的秩一节，补上了维特定理的证明，增加了附录四中有理标准形的内容，适当补充了数字资源。 本书主要内容是：多项式、行列式、线性方程组、矩阵、二次型、线性空间、线性变换、λ-矩阵、欧几里得空间、双线性函数与辛空间、总习题，附录包括关于连加号“∑”、整数的可除性理论、代数基本定理的证明、##-矩阵与矩阵相似标准形的几何理论。 本书适合作为高等学校数学类专业高等代数教材和教学参考书。'),(13,'离散数学及其应用','9787111642176','肯尼思·H. 罗森','机械工业出版社',79,'2020-09-28 11:35:00',2,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01IMeqrc2KWMY7z4btA_!!0-item_pic.jpg_430x430q90.jpg','本书是经典的离散数学教材，被全球数百所大学广为采用。本科教学版缩减了篇幅，保留的主要内容包括：逻辑和证明，集合、函数、序列、求和与矩阵，计数，关系，图，树，布尔代数。全书取材广泛，除包括定义、定理的严格陈述外，还配备大量的例题、图表、应用实例和练习。第8版做了与时俱进的更新，成为更加实用的教学工具。本书可作为高等院校数学、计算机科学和计算机工程等专业的教材，也可作为科技领域从业人员的参考书。'),(14,'数据结构 严蔚敏 C语言版','9787113170202','左飞','中国铁道出版社',55,'2020-09-28 11:38:00',1,'https://img.alicdn.com/imgextra/i4/2451699564/O1CN017NK9XJ2KWMaxu3OD8_!!0-item_pic.jpg_430x430q90.jpg','　　本书与清华大学出版社出版的《数据结构》(c语言版)一书相配套，主要内容有：习题与学习指导、实习题和部分习题的提示或答案三大部分和一个附录[“数据结构算法演示系统(类c描述语言3．1中文版)使用手册”，此软件已由清华大学出版社出版]。 \r\n　　其中习题篇的内容和《数据结构》(c语言版)一书相对应，也分为12章，每一章大致由基本内容、学习要点、算法演示内容及基础知识题和算法设计题五部分组成。实习题分成六组，每一组都有鲜明的主题，围绕1到2种数据结构，安排4到9个题，每个题都有明确的练习目的和要求，在每一组中都给出一个实习报告的范例，以供读者参考。 \r\n　　本书内容丰富、程序设汁观点新颖，在内容的详尽程度上接近课程辅导材料，不仅可作为大专院校的配套教材，也是广大丁程技术人员和自学读者颇有帮助的辅助教材。\r\n\r\n'),(15,'汇编语言程序设计（第5五版）','9787121315886','钱晓捷','电子工业出版社',52,'2020-09-28 11:40:00',1,'https://img.alicdn.com/imgextra/i3/1932014659/O1CN01qaQrxo1kHrr7VhnFe_!!1932014659.jpg_430x430q90.jpg','本书为“十二五”普通高等教育本科***规划教材，是***‐微软精品课程教学成果。本书以Intel 80x86指令系统和MASM 6.x为主体，共10章，分为基础和提高两部分。前5章为基础部分，以当前“汇编语言程序设计”课程的教学为目标，讲解16位基本整数指令及其汇编语言程序设计的知识，包括：汇编语言程序设计基础知识，8086指令详解，MASM伪指令和操作符，程序格式，程序结构及其设计方法。后5章为提高部分，介绍汇编语言程序设计的深入内容和实际应用知识，包括：32位80x86 CPU的整数指令系统及其编程，汇编语言与C/C++混合编程，80x87 FPU浮点指令系统及其编程，多媒体扩展指令系统及其编程，64位指令简介。本书可作为高等院校“汇编语言程序设计”课程的教材或参考书。本书内容广博、语言浅显、结构清晰、实例丰富，也适合电子信息、自动控制等专业的高校学生和成教学生、计算机应用开发人员、深入学习微机应用技术的普通读者阅读。'),(16,'单片机原理及应用 第4版','9787121320729','姜志海 黄玉清','电子工业出版社',40,'2020-09-28 11:40:00',1,'https://img.alicdn.com/imgextra/i1/1932014659/TB2zyMeXwoQMeJjy1XaXXcSsFXa_!!1932014659.jpg_430x430q90.jpg','《单片机原理及应用技术：基于C51的Proteus仿真及实板案例（第4版）/“十二五”职业教育国家规划教材》中系统地介绍了80C51系列单片机的原理及应用技术，较好地体现了应用型人才的培养要求，其特点为：着力片上资源、强化编程训练。尽管新型单片机芯片不断推出，但片上基本资源仍保持稳定。掌握单片机技术就是用应用程序调度单片机片内及扩展资源的工作。\r\n\r\n本书采用C51语言为编程工具讲述程序的设计方法与技巧，并将开发平台Vision的运用、Proteus软件仿真及实板验证贯穿教材的始终。适合教师讲授、易于学生阅读。本书选材规范，通俗易懂，每章都配有小结、思考题及实训内容。对教师提供配套课件。该教材在串行扩展、C51应用实践和Proteus仿真方面特色突出，反映了单片机应用技术的发展趋势。\r\n\r\n本书可以作为高职高专自动化、计算机、电气技术、应用电子技术以及机电一体化等专业的教材。');

/*Table structure for table `sys_book_banner` */

DROP TABLE IF EXISTS `sys_book_banner`;

CREATE TABLE `sys_book_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `resource_url` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `b_id` int(11) DEFAULT NULL COMMENT '图书id',
  PRIMARY KEY (`id`),
  KEY `BOOK_BANNER_BIO_BOOK` (`b_id`),
  CONSTRAINT `BOOK_BANNER_BIO_BOOK` FOREIGN KEY (`b_id`) REFERENCES `sys_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_book_banner` */

insert  into `sys_book_banner`(`id`,`resource_url`,`modify_time`,`b_id`) values (1,'http://img10.360buyimg.com/babel/s1580x740_jfs/t1/131340/12/10616/178525/5f6b24d2E45bf8dae/0db462494cdef473.jpg!cc_1580x740.webp','2020-09-29 17:08:00',2),(2,'http://img14.360buyimg.com/babel/s1580x740_jfs/t1/111418/9/18940/68815/5f718b34Ed9e0046a/82a254f70bdd48b0.jpg!cc_1580x740.webp','2020-09-29 17:08:00',1),(3,'http://img30.360buyimg.com/babel/s1580x740_jfs/t1/122105/15/13850/291647/5f718886E4f525f84/a26f49f1faa479ad.jpg!cc_1580x740.webp','2020-09-29 17:08:00',3),(4,'http://img30.360buyimg.com/babel/s1580x740_jfs/t1/132469/18/10643/164916/5f69ba9bE7a21fb3c/62d6f8c77fc4d9e1.jpg!cc_1580x740.webp','2020-09-29 17:08:00',4);

/*Table structure for table `sys_book_resource` */

DROP TABLE IF EXISTS `sys_book_resource`;

CREATE TABLE `sys_book_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `b_id` int(11) DEFAULT NULL COMMENT '图书表的外键字段',
  `resource_url` varchar(200) DEFAULT NULL COMMENT '对应图书的资源路径',
  `symbol` varchar(20) DEFAULT NULL COMMENT '标识，用于区分资源类型',
  `supplement` varchar(50) DEFAULT NULL COMMENT '补充说明',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `BOOK_RESOURCE_BID_BOOK` (`b_id`),
  CONSTRAINT `BOOK_RESOURCE_BID_BOOK` FOREIGN KEY (`b_id`) REFERENCES `sys_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `sys_book_resource` */

insert  into `sys_book_resource`(`id`,`b_id`,`resource_url`,`symbol`,`supplement`,`create_time`) values (1,1,'https://img.alicdn.com/imgextra/i3/2451699564/TB2NCz5sVXXXXXhXFXXXXXXXXXX_!!2451699564.jpg_430x430q90.jpg','cover','《算法导论》封面图','2020-09-18 15:35:00'),(3,2,'https://img.alicdn.com/imgextra/i3/288902762/O1CN01Bznidn1WH2XUXELCk_!!0-item_pic.jpg_430x430q90.jpg','cover','《Spring Cloud Alibaba》封面图','2020-09-18 15:35:00'),(5,3,'https://img.alicdn.com/imgextra/i4/2451699564/O1CN01giaKdB2KWMYQj6XOJ_!!2451699564.jpg_430x430q90.jpg','cover','《深入理解Java虚拟机》封面图','2020-09-28 11:19:00'),(6,4,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01urG1AV1vSMcEV5nWM_!!2649526171.jpg_430x430q90.jpg','cover','《Spring Boot+Vue全栈开发实战》封面图','2020-09-28 11:21:00'),(7,5,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01jqAEBz2KWMUVxLUj9_!!2451699564.jpg_430x430q90.jpg','cover','《深入浅出Vue.js》封面图','2020-09-28 11:22:00'),(8,6,'https://img.alicdn.com/imgextra/i3/2200707119547/O1CN01VcpAIK2KOZr49daIf_!!0-item_pic.jpg_430x430q90.jpg','cover','《Node.js+Webpack开发实战》封面图','2020-09-28 11:22:00'),(9,7,'https://img.alicdn.com/imgextra/i4/2130152348/O1CN01QntL051TDQXXkunxf_!!0-item_pic.jpg_430x430q90.jpg','cover','《深入浅出Node》封面图','2020-09-28 11:23:00'),(10,8,'https://img.alicdn.com/imgextra/i2/381191231/O1CN01il5ylE1Kxq9OvgdUr_!!0-item_pic.jpg_430x430q90.jpg','cover','《SpringCloud微服务架构实战》封面图','2020-09-28 11:24:00'),(11,9,'https://img.alicdn.com/imgextra/i2/2397078731/O1CN01EqHHTp2EMqXQN7Ijy_!!0-item_pic.jpg_430x430q90.jpg','cover','《RocketMQ技术内幕》封面图','2020-09-28 11:29:00'),(12,10,'https://img.alicdn.com/imgextra/i1/1932014659/O1CN011kHrjx7e93iBpUN_!!0-item_pic.jpg_430x430q90.jpg','cover','《C++ Primer 英文版》封面图','2020-09-28 11:30:00'),(13,11,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01tOpC1a2KWMXsR0iZZ_!!2451699564.jpg_430x430q90.jpg','cover','《数学分析华东师大 第五版 上册+下册》封面图','2020-09-28 11:34:00'),(14,12,'https://img.alicdn.com/imgextra/i2/2451699564/O1CN011ntHvJ2KWMWxa6yd8_!!0-item_pic.jpg_430x430q90.jpg','cover','《高等代数 第五版 北大五版》封面图','2020-09-28 11:34:00'),(15,13,'https://img.alicdn.com/imgextra/i1/2451699564/O1CN01IMeqrc2KWMY7z4btA_!!0-item_pic.jpg_430x430q90.jpg','cover','《离散数学及其应用》封面图','2020-09-28 11:35:00'),(16,14,'https://img.alicdn.com/imgextra/i4/2451699564/O1CN017NK9XJ2KWMaxu3OD8_!!0-item_pic.jpg_430x430q90.jpg','cover','《数据结构 严蔚敏 C语言版》封面图','2020-09-28 11:38:00'),(17,15,'https://img.alicdn.com/imgextra/i3/1932014659/O1CN01qaQrxo1kHrr7VhnFe_!!1932014659.jpg_430x430q90.jpg','cover','《汇编语言程序设计（第5五版）》封面图','2020-09-28 11:40:00'),(18,16,'https://img.alicdn.com/imgextra/i1/1932014659/TB2zyMeXwoQMeJjy1XaXXcSsFXa_!!1932014659.jpg_430x430q90.jpg','cover','《单片机原理及应用 第4版》封面图','2020-09-28 11:40:00'),(19,2,'https://img.alicdn.com/imgextra/i2/2598292358/O1CN01vSqgxI1TI0WInqZFl_!!2598292358.gif','detail1','《Spring Cloud Alibaba》详情长图','2020-09-28 22:13:00'),(20,1,'https://img.alicdn.com/imgextra/i2/2451699564/TB2EucjsVXXXXa9XpXXXXXXXXXX_!!2451699564.jpg','detail1','《算法导论》详情图1','2020-09-28 22:13:00'),(21,1,'https://img.alicdn.com/imgextra/i2/2451699564/TB2wPwysVXXXXXeXpXXXXXXXXXX_!!2451699564.jpg','detail2','《算法导论》详情图2','2020-09-28 22:13:00'),(22,1,'https://img.alicdn.com/imgextra/i3/2451699564/TB2yP7CsVXXXXcTXXXXXXXXXXXX_!!2451699564.jpg','detail3','《算法导论》详情图3','2020-09-28 22:13:00'),(23,1,'https://img.alicdn.com/imgextra/i3/2451699564/TB2l4gUsVXXXXa1XXXXXXXXXXXX_!!2451699564.jpg','detail4','《算法导论》详情图4','2020-09-28 22:13:00'),(24,1,'https://img.alicdn.com/imgextra/i1/2451699564/TB2RgA4sVXXXXXSXXXXXXXXXXXX_!!2451699564.jpg','detail5','《算法导论》详情图4','2020-09-28 22:13:00'),(25,3,'https://img.alicdn.com/imgextra/i4/3446196188/O1CN01voxoYB1va9Qr4uHjf_!!3446196188.jpg','detail1','《深入理解Java虚拟机》详情图1','2020-09-28 22:13:00'),(26,3,'https://img.alicdn.com/imgextra/i2/3446196188/O1CN01yzq9ii1va9QtRXEhp_!!3446196188.jpg','detail2','《深入理解Java虚拟机》详情图2','2020-09-28 22:13:00'),(27,3,'https://img.alicdn.com/imgextra/i1/3446196188/O1CN01CkB0e01va9QqlEZcT_!!3446196188.jpg','detail3','《深入理解Java虚拟机》详情图3','2020-09-28 22:13:00'),(28,3,'https://img.alicdn.com/imgextra/i1/3446196188/O1CN01T9ccuf1va9QsVrBVR_!!3446196188.jpg','detail4','《深入理解Java虚拟机》详情图4','2020-09-28 22:13:00'),(29,4,'https://img.alicdn.com/imgextra/i3/2649526171/O1CN01sSbHC01vSMg0CuX0Z_!!2649526171.jpg','detail1','《Spring Boot+Vue全栈开发实战》详情图1','2020-09-28 22:13:00'),(30,4,'https://img.alicdn.com/imgextra/i2/2649526171/O1CN01bIUhEY1vSMg7FZhDD_!!2649526171.jpg','detail2','《Spring Boot+Vue全栈开发实战》详情图2','2020-09-28 22:13:00'),(31,4,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01BiJ3fd1vSMgBXHVOd_!!2649526171.jpg','detail3','《Spring Boot+Vue全栈开发实战》详情图3','2020-09-28 22:13:00'),(32,4,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01tocS7C1vSMg7wxRnX_!!2649526171.jpg','detail4','《Spring Boot+Vue全栈开发实战》详情图4','2020-09-28 22:13:00'),(33,4,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01keZ57j1vSMg7FYYVp_!!2649526171.jpg','detail5','《Spring Boot+Vue全栈开发实战》详情图5','2020-09-28 22:13:00'),(34,4,'https://img.alicdn.com/imgextra/i1/2649526171/O1CN01nZTIfQ1vSMg8g8ttM_!!2649526171.jpg','detail6','《Spring Boot+Vue全栈开发实战》详情图6','2020-09-28 22:13:00'),(35,4,'https://img.alicdn.com/imgextra/i4/2649526171/O1CN01GSBaZE1vSMgA7ukua_!!2649526171.jpg','detail7','《Spring Boot+Vue全栈开发实战》详情图7','2020-09-28 22:13:00'),(36,4,'https://img.alicdn.com/imgextra/i1/2649526171/O1CN01Mb79qn1vSMg8g8MeX_!!2649526171.jpg','detail8','《Spring Boot+Vue全栈开发实战》详情图8','2020-09-28 22:13:00');

/*Table structure for table `sys_book_storage` */

DROP TABLE IF EXISTS `sys_book_storage`;

CREATE TABLE `sys_book_storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长字段',
  `b_id` int(11) DEFAULT NULL COMMENT '图书表的外键字段',
  `last_add_time` datetime DEFAULT NULL COMMENT '最后一次添加时间',
  `residue_count` int(11) DEFAULT '0' COMMENT '剩余数量',
  PRIMARY KEY (`id`),
  KEY `STORAGE_BID_BOOK` (`b_id`),
  CONSTRAINT `STORAGE_BID_BOOK` FOREIGN KEY (`b_id`) REFERENCES `sys_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `sys_book_storage` */

insert  into `sys_book_storage`(`id`,`b_id`,`last_add_time`,`residue_count`) values (1,1,'2020-09-18 15:35:00',16),(2,2,'2020-09-18 15:35:00',23),(3,3,'2020-09-28 11:19:00',25),(4,4,'2020-09-28 11:21:00',20),(5,5,'2020-09-28 11:22:00',17),(6,6,'2020-09-28 11:22:00',19),(7,7,'2020-09-28 11:23:00',17),(8,8,'2020-09-28 11:24:00',29),(9,9,'2020-09-28 11:29:00',22),(10,10,'2020-09-28 11:30:00',40),(11,11,'2020-09-28 11:34:00',38),(12,12,'2020-09-28 11:34:00',44),(13,13,'2020-09-28 11:35:00',85),(14,14,'2020-09-28 11:38:00',59),(15,15,'2020-09-28 11:40:00',53),(16,16,'2020-09-28 11:40:00',46);

/*Table structure for table `sys_book_type` */

DROP TABLE IF EXISTS `sys_book_type`;

CREATE TABLE `sys_book_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
  `type` varchar(10) DEFAULT NULL COMMENT '分类',
  `supplement` varchar(30) DEFAULT NULL COMMENT '补充说明',
  `img` varchar(200) DEFAULT NULL COMMENT '类型图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `sys_book_type` */

insert  into `sys_book_type`(`id`,`type`,`supplement`,`img`) values (1,'计算机','无','https://img13.360buyimg.com/n2/jfs/t6130/167/771989293/235186/608d0264/592bf167Naf49f7f6.jpg'),(2,'数学','无','https://img10.360buyimg.com/n7/jfs/t1/43273/22/6885/205800/5d0887e3E0220300a/2f8899533ce0060e.jpg'),(3,'英语','无','https://img13.360buyimg.com/n1/s200x200_jfs/t1/16923/19/10209/110434/5c861fe6E4d5ca26c/f096adeddc3897b4.jpg'),(4,'原版书','无','https://img10.360buyimg.com/n2/jfs/t751/83/978033592/55356/41b246f2/550fa112Naa9e47f2.jpg'),(5,'法律','无','https://img11.360buyimg.com/n2/jfs/t1/112002/12/8861/206741/5ed34324Ea1e06388/d4dac59ffde42c36.jpg'),(6,'文学','无','https://img11.360buyimg.com/n2/jfs/t1/115800/7/11521/168011/5efd502bE35e0e166/29dc49b57e724528.jpg'),(7,'童书','无','https://img10.360buyimg.com/n2/jfs/t1/109357/28/8559/269520/5e699be8E454165c5/c341273b6cca4885.jpg.webp'),(8,'教材教辅','无','https://img12.360buyimg.com/n1/s200x200_jfs/t1/92109/20/10243/197980/5e185974Efa6d9f46/d38957e150bfa528.jpg'),(9,'经营励志','无','https://img13.360buyimg.com/n2/jfs/t1/8146/1/12459/224188/5c34464cE94474213/6a2383acd0caaa20.jpg'),(10,'艺术','无','https://img10.360buyimg.com/n2/jfs/t1/87515/12/13553/138458/5e59c68fE8422aea0/0dd4d0319cc3948b.jpg'),(11,'科学技术','无','https://img14.360buyimg.com/n2/jfs/t22768/361/2640551942/174733/8d3a500d/5b8a4ae0Ncf2c12c4.jpg'),(12,'政治军事','无','https://img10.360buyimg.com/n2/jfs/t1/100325/7/270/332730/5da9d991E7ba8bbde/c00f8b29c015d880.jpg'),(13,'传记','无','https://img11.360buyimg.com/n1/s200x200_jfs/t1270/255/1452364868/208688/8a6b6743/572c698dN0f9a3440.jpg'),(14,'手工游戏','无','https://img14.360buyimg.com/n1/s200x200_jfs/t1/112587/8/13299/484310/5f1d1842E2cb348b6/7c593b73a77e67f9.jpg'),(15,'书法','无','https://img10.360buyimg.com/n1/s200x200_jfs/t11482/176/1261957082/264245/1f4f4313/59ffd31fN69f4bfa5.jpg'),(16,'绘画','无','https://img13.360buyimg.com/n2/jfs/t17443/291/2027088687/178730/84da783b/5ae42177N4cd202a7.jpg'),(17,'烹饪美食','无','https://img10.360buyimg.com/n1/s200x200_g9/M00/11/0B/rBEHaVDX2x4IAAAAAAiaHzfGoiIAADZHwLsGzAACJo3523.jpg'),(18,'国学古籍','无','https://img10.360buyimg.com/n1/s200x200_g15/M02/16/11/rBEhWFKJiXcIAAAAAAYpnmfSkvkAAFnBgF7dHcABim2200.jpg'),(19,'医学','无','https://img11.360buyimg.com/n2/jfs/t30802/238/1227068679/140226/a6a4d7e0/5cda72c4N42c162a2.jpg'),(20,'字典词典','无','https://img10.360buyimg.com/n2/jfs/t5602/297/3129967787/297801/b02e5c07/5937718bN6d4c1b7d.jpg');

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
