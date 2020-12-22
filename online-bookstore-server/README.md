### 项目配置说明

以下表格仅是开发环境的项目情况，所有微服务的bootstrap.yaml文件都是依照下表进行配置，线上部署则会改变

|                         |       环境        |     ip地址      | 端口号 |
| :---------------------: | :---------------: | :-------------: | :----: |
|       用户微服务        |     windows10     |    localhost    |  7000  |
|       图书微服务        |     windows10     |    localhost    |  7100  |
|       订单微服务        |     windows10     |    localhost    |  7200  |
|          网关           |     windows10     |    localhost    |  9527  |
| nacos服务注册与配置中心 |      CentOS7      | 101.200.203.216 |  8848  |
|        RocketMQ         |      CentOS7      | 112.126.78.225  |        |
|        Sentinel         |     windows10     |    localhost    |  8080  |
|          Seata          |      CentOS7      | 132.232.28.130  |  8091  |
|          Redis          |      CentOS7      | 101.200.203.216 |  6379  |
|          MySQL          |      CentOS7      | 101.200.203.216 |  3306  |
|         Zipkin          | CentOS7（Docker） | 101.200.203.216 |  9411  |

项目中配置文件为了供各位参考，因此在提交前我已经把nacos配置中心的所有配置文件都存放到了各个微服务的bootstrap.yml里面了。

###运行项目的准备工作

以下所有的部署均在linux的CentOS7上进行，我使用的是阿里云服务器，如果没有，需要使用虚拟机进行安装centos7操作系统

###Nginx安装

该项目中的图片文件存放，以及最终vue代码的部署需要用到，但由于我自己的服务器是1核2G，最终不会部署到云服务器上，因为内存不允许，因此暂时只用作文件服务器。

#### 安装包安装

安装nginx需要的依赖

```shell
[root@112 software]# yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel
下载nginx安装包，没有wget命令的需要先安装wget命令
[root@112 software]# wget -c https://nginx.org/download/nginx-1.12.0.tar.gz
```

解压nginx并编译

```shell
[root@112 software]# tar -zxvf nginx-1.12.0.tar.gz
[root@112 software]# cd nginx-1.12.0
[root@112 nginx-1.12.0]# ./configure 
注：将临时文件目录指定为/var/temp/nginx，需要在/var下创建temp及nginx目录
[root@112 nginx-1.12.0]# make
[root@112 nginx-1.12.0]# make install
```

编译完成后，将会在/usr/local/下生成nginx目录，进入到nginx目录

```shell
[root@112 software]# cd /usr/local/nginx/
[root@112 nginx]# cd sbin/
启动、停止nginx
[root@112 sbin]# ./nginx 
[root@112 sbin]# ./nginx -s stop
[root@112 sbin]# ./nginx -s quit
[root@112 sbin]# ./nginx -s reload
```

当启动成功后，通过浏览器，访问http://ip地址/，这里的ip地址即是安装nginx的机器的ip地址，该项目的nginx是在112.126.78.225机器上，因此是访问：http://112.126.78.225/，如果能够看到：Welcome to nginx!，就说明nginx安装成功，默认启动端口时80端口

添加开机自启动，编辑配置文件

```shell
[root@112 sbin]# vi /etc/rc.local
最后一行添加：/usr/local/nginx/sbin/nginx
添加权限
[root@112 sbin]# chmod 755 /etc/rc.local
```

### Nginx做文件服务器

#### 添加用户

```shell
准备创建一个用户来管理文件（root操作下创建）
[root@112 ~]# useradd ftpadmin
修改用户密码
[root@112 ~]# passwd ftpadmin 123
指定ftpadmin用户的文件所有者
[root@112 ~]# chown ftpadmin /home/ftpadmin
修改权限
[root@112 ~]# chmod 777 -R /home/ftpadmin
在ftpadmin用户的家目录下创建images目录，该目录即是该项目的图片存储位置，一会将会通过nginx的映射与该目录关联，这样上传的文件就会在这个文件夹目录下，通过nginx即可直接进行http的地址访问图片，便于前端和安卓端显示图片。
[root@112 ~]# mkdir /home/ftpadmin/images
```

####Nginx配置

```shell
进入到刚刚安装的nginx，准备修改配置
[root@112 ~]# cd /usr/local/nginx/conf/
[root@112 conf]# vim nginx.conf
```

在http下的server下添加location，以下有注释说明意思

```conf
server {
        listen       80;
        server_name  localhost;

        # 添加location并指定实际路径
        location /images/ {
            root  /home/ftpadmin/;      #将images映射到/home/ftpadmin/img
            autoindex on;               #打开浏览功能
        }

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }
        ...
}
```

重新加载nginx

```shell
[root@112 conf]# cd ../sbin/
[root@112 sbin]# ./nginx -s reload
```

测试是否配置成功：
使用Xftp工具或者使用命令上传一张图片到/home/ftpadmin/images下，通过浏览器访问：
http://112.126.78.225/images/36406ddea66a4f9b92d74096a21032f1.jpg
如果能够看到图片显示说明配置成功，当然，ip地址需要改成自己的nginx的ip地址，项目的数据库会存放该图片的url，数据返回给前端，前端直接使用url找nginx请求图片资源。

###Redis安装

redis在项目中起到了很大的作用，在图书微服务的热点数据的存储以及用户微服务的验证码、黑名单等功能都有应用，因此，运行项目之前安装redis是很有必要的

安装redis需要的依赖

```shell
[root@101 ~]# yum install cpp
[root@101 ~]# yum install binutils
[root@101 ~]# yum install glibc
[root@101 ~]# yum install glibc-kernheaders
[root@101 ~]# yum install glibc-common
[root@101 ~]# yum install glibc-devel
[root@101 ~]# yum install gcc
[root@101 ~]# yum install make
```

安装redis

```shell
[root@101 ~]# cd /usr/local/
[root@101 local]# wget http://download.redis.io/releases/redis-5.0.3.tar.gz
解压
[root@101 local]# tar -zxvf redis-5.0.3.tar.gz
进入redis并进行编译
[root@101 local]# cd redis-5.0.3
[root@101 redis-5.0.3]# make
开始安装并指定安装目录，备份初始配置文件
[root@101 redis-5.0.3]# make install PREFIX=/usr/local/redis
复制配置文件，如果redis/bin下有该文件可以不用操作
[root@101 redis-5.0.3]# cp redis.conf /usr/local/redis/bin/
进入到刚刚指定的redis目录
[root@101 redis-5.0.3]# cd /usr/local/redis/bin
将配置文件保存一份，避免修改错误，如果该目录下没有redis.conf文件，就从redis-5.0.3目录
[root@101 bin]# cp redis.conf redis.conf.bk
```

修改配置文件，因为我们的redis需要在后台运行

```shell
[root@101 bin]# vim redis.conf
```

将daemonize修改为yes（运行后台运行），注释掉bind 127.0.0.1可以让所有的ip访问redis，修改protected-mode为no

```conf
################################# GENERAL #####################################

# By default Redis does not run as a daemon. Use 'yes' if you need it.
# Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
daemonize yes

# ~~~ WARNING ~~~ If the computer running Redis is directly exposed to the
# internet, binding to all the interfaces is dangerous and will expose the
# instance to everybody on the internet. So by default we uncomment the
# following bind directive, that will force Redis to listen only into
# the IPv4 loopback interface address (this means Redis will be able to
# accept connections only from clients running into the same computer it
# is running).
#
# IF YOU ARE SURE YOU WANT YOUR INSTANCE TO LISTEN TO ALL THE INTERFACES
# JUST COMMENT THE FOLLOWING LINE.
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# bind 127.0.0.1

# By default protected mode is enabled. You should disable it only if
# you are sure you want clients from other hosts to connect to Redis
# even if no authentication is configured, nor a specific set of interfaces
# are explicitly listed using the "bind" directive.
protected-mode no
```

添加开机启动服务

```shell
[root@101 bin]# vim /etc/systemd/system/redis.service
```

添加以下内容

```shell
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```

设置开机启动

```shell
[root@101 bin]# systemctl daemon-reload
[root@101 bin]# systemctl start redis.service
[root@101 bin]# systemctl enable redis.service
```

创建redis命令软链接，以便在任何目录下使用redis即可进入redis控制台

```shell
[root@101 ~]# ln -s /usr/local/redis/bin/redis-cli /usr/bin/redis
```

进入redis

```shell
[root@101 ~]# redis
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> 
测试成功！
```

基本命令
systemctl start redis.service   #启动redis服务
systemctl stop redis.service   #停止redis服务
systemctl restart redis.service   #重新启动服务
systemctl status redis.service   #查看服务当前状态
systemctl enable redis.service   #设置开机自启动
systemctl disable redis.service   #停止开机自启动

###CentOS7下jdk8安装

以下很多安装的部署都需要基于jdk，因此下面将是jdk8的配置

```shell
将jdk上传到/usr/local/java目录下，如果没有java目录，mkdir java进行创建，解压
[root@112 java]# tar -zxvf jdk-8u11-linux-x64.tar.gz
mv命令重命名jdk为jdk1.8，配置环境变量
[root@112 java]# vim /etc/profile

在配置文件最后进行配置
JAVA_HOME=/usr/local/java/jdk1.8
JRE_HOME=/usr/local/java/jdk1.8/jre
CLASS_PATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib
PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export JAVA_HOME JRE_HOME CLASS_PATH PATH


刷新配置
[root@112 java]# source /etc/profile
测试jdk配置是否成功
[root@112 java]# java -version
```

### CentOS7下maven安装

```shell
maven包上传到/usr/local，进行解压并指定解压位置
[root@112 local]# tar zxvf apache-maven-3.6.3-bin.tar.gz -C /opt/inst
创建本地仓库
[root@112 local]# mkdir /opt/jarstore
[root@112 inst]# cd /opt/inst
重命名目录
[root@112 inst]# mv apache-maven-3.6.3/ maven363
```

#####修改本地仓库地址

```shell
[root@112 inst]# cd maven363/conf/
[root@112 inst]# vim settings.xml
```

```xml
<!-- localRepository
| The path to the local repository maven will use to store artifacts.
|
| Default: ${user.home}/.m2/repository
<localRepository>/path/to/local/repo</localRepository>
-->
<localRepository>/opt/jarstore</localRepository>
```

##### 修改镜像地址为阿里地址

```xml
<mirrors>
    <!-- mirror
     | Specifies a repository mirror site to use instead of a given repository. The repository that
     | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
     | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
     |
    <mirror>
      <id>mirrorId</id>
      <mirrorOf>repositoryId</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://my.repository.com/repo/path</url>
    </mirror>
     -->
    <mirror>
      <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>aliyun maven</name>
        <url>https://maven.aliyun.com/repository/central</url>
    </mirror>

    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    
  </mirrors>
```

### CentOS7下安装mysql5.7

因为整合seata需要用到mysql，因此以下为mysql的安装

* 检查mysql用户组和用户是否存在，如果没有，则创建

  ```shell
  [root@localhost /]# cat /etc/group | grep mysql
  [root@localhost /]# cat /etc/passwd |grep mysql
  [root@localhost /]# groupadd mysql
  [root@localhost /]# useradd -r -g mysql mysql
  ```

* 从官网下载用于Linux的MySQL安装包

  ```shell
  [root@localhost /]#  wget https://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz
  ```

* 解压

  ```shell
  [root@localhost /]#  tar xzvf mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz
  [root@localhost /]# ls
  mysql-5.7.24-linux-glibc2.12-x86_64
  mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz
  ```

* 将解压文件移动到/usr/local下，并将文件夹名称改为mysql

  ```shell
  [root@localhost /]# mv mysql-5.7.24-linux-glibc2.12-x86_64 /usr/local/
  [root@localhost /]# cd /usr/local/
  [root@localhost /]# mv mysql-5.7.24-linux-glibc2.12-x86_64 mysql
  ```

* 在**/usr/local/mysql**目录下创建data目录

  ```shell
  [root@localhost /]# mkdir /usr/local/mysql/data
  ```

* 更改mysql目录下所有的目录及文件夹所属的用户组和用户，以及权限

  ```shell
  [root@localhost /]# chown -R mysql:mysql /usr/local/mysql
  [root@localhost /]# chmod -R 755 /usr/local/mysql
  ```

* 编译安装并初始化mysql，**务必记住初始化输出日志末尾的密码（数据库管理员临时密码）**

  ```shell
  [root@localhost /]# cd /usr/local/mysql/bin
  [root@localhost bin]# ./mysqld --initialize --user=mysql --datadir=/usr/local/mysql/data --basedir=/usr/local/mysql
  ```

* 编辑配置文件my.cnf，添加配置如下

  ```shell
  [root@localhost bin]#  vi /etc/my.cnf
  
  [mysqld]
  datadir=/usr/local/mysql/data
  port=3306
  sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
  symbolic-links=0
  max_connections=600
  innodb_file_per_table=1
  lower_case_table_names=1
  character-set-server=utf8
  default-storage-engine=INNODB
  ```

* 测试启动mysql服务器

  ```shell
  [root@localhost /]# /usr/local/mysql/support-files/mysql.server start
  ```

* 添加软连接，并重启mysql服务

  ```shell
  [root@localhost /]#  ln -s /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql 
  [root@localhost /]#  ln -s /usr/local/mysql/bin/mysql /usr/bin/mysql
  [root@localhost /]#  service mysql restart
  ```

* 登录mysql，修改密码（密码为之前生成的**临时密码**）

  ```shell
  [root@localhost /]#  mysql -u root -p
  Enter password:
  mysql>set password for root@localhost = password('yourpass');
  ```

  如果报错ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this statement.

  直接执行

  ```mysql
  mysql> alter user user() identified by "root";
  ```

* 开放远程连接

  ```mysql
  mysql>use mysql;
  msyql>update user set user.Host='%' where user.User='root';
  mysql>flush privileges;
  ```

* 设置开机自动启动

  ```shell
  1、将服务文件拷贝到init.d下，并重命名为mysql
  [root@localhost /]# cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysqld
  2、赋予可执行权限
  [root@localhost /]# chmod +x /etc/init.d/mysqld
  3、添加服务
  [root@localhost /]# chkconfig --add mysqld
  4、显示服务列表
  [root@localhost /]# chkconfig --list
  ```

  

###RocketMQ安装

####RocketMQ基本环境搭建

消息中间件在高并发项目中起了很重要的作用，不管是解耦还是削峰，该项目中使用消息中间件的延迟消息，因此，运行该项目的要安装消息中间件，而消息中间件产品很多，我的技术选型选择了RocketMQ，以下是安装步骤

首先从官网http://rocketmq.apache.org/docs/quick-start/下载安装包，并且上传到linux服务器上

```shell
[root@112 ~]# cd /usr/local/software/
假设已经上传到了software目录，我们进行解压
[root@112 software]# unzip rocketmq-all-4.7.1-source-release.zip
创建一个rocketmq目录
[root@112 software]# mkdir rocketmq
将解压的目录移动到rocketmq目录下
[root@112 software]# mv rocketmq-all-4.7.1-source-release rocketmq
进入rocketmq目录
[root@112 software]# cd rocketmq

由于rocketmq是一个maven项目，因此我们需要下载对应的依赖（确保在接下的操作前已经成功配置了jdk和maven环境）
[root@112 rocketmq]# mvn -Prelease-all -DskipTests clean install -U
[root@112 rocketmq]# cd distribution/target/rocketmq-4.7.1/rocketmq-4.7.1
启动测试
[root@112 rocketmq-4.7.1]# sh bin/mqnamesrv

若启动报错显示内存不足，需要修改bin文件夹下的runserver.sh和runbroker.sh脚本，修改启动参数
[root@112 rocketmq-4.7.1]# cd bin
[root@112 bin]# vim runserver.sh
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn125m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
[root@112 bin]# vim runbroker.sh
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn125m"

再次启动测试
[root@112 bin]# cd ..
[root@112 rocketmq-4.7.1]# sh bin/mqnamesrv


测试成功，按ctrl+c退出前台启动，服务也将停止，因此我们需要使用nohup进行后台启动
守护进程的方式启动nameServer
[root@112 rocketmq-4.7.1]# nohup sh bin/mqnamesrv &
[1] 13069
[root@112 rocketmq-4.7.1]# nohup: ignoring input and appending output to 'nohup.out'
^C
查看启动的状态
[root@112 rocketmq-4.7.1]# tail -f nohup.out
Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
The Name Server boot success. serializeType=JSON
[root@VM-0-4-centos rocketmq-4.7.1]# jps
13076 NamesrvStartup
13207 Jps


配置broker
[root@112 rocketmq-4.7.1]# cd conf/
[root@112 conf]# vim broker.conf
修改配置，brokerIP1为启动的公网ip，因为我是阿里云服务器，要开发给本地的java代码访问，如果是虚拟机也可以使用局域网ip。namesrvAddr为指定namesrv的地址，因为机器性能不够，因此不考虑MQ的高可用，namesrv也是与broker在同一个机器上，因此直接指定。

brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1 = 112.126.78.225
namesrvAddr = 112.126.78.225:9876



启动broker（启动broker之前要启动namesrv），-n参数是指定namesrv的地址，-c参数是指定配置启动需要的配置文件，我们使用刚刚修改的配置文件
[root@VM-0-4-centos conf]# cd ..
[root@VM-0-4-centos rocketmq-4.7.1]# nohup sh bin/mqbroker -n localhost:9876 & -c ./conf/broker.conf
[2] 13714
[root@VM-0-4-centos rocketmq-4.7.1]# nohup: ignoring input and appending output to 'nohup.out'
^C
[root@VM-0-4-centos rocketmq-4.7.1]# tail -f nohup.out
Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
The Name Server boot success. serializeType=JSON
The broker[VM-0-4-centos, 172.27.0.4:10911] boot success. serializeType=JSON and name server is localhost:9876
启动成功

测试使用
#设置名称服务地址
[root@112 rocketmq-4.7.1]# export NAMESRV_ADDR=localhost:9876
#投递消息
[root@112 rocketmq-4.7.1]# sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
#消费消息
[root@112 rocketmq-4.7.1]# sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer

关闭nameServer和broker
[root@112 rocketmq-4.7.1]# sh bin/mqshutdown broker
The mqbroker(36695) is running...
Send shutdown request to mqbroker(36695) OK

[root@112 rocketmq-4.7.1]# sh bin/mqshutdown namesrv
The mqnamesrv(36664) is running...
Send shutdown request to mqnamesrv(36664) OK

启动后台项目前一定要启动MQ，启动命令
[root@112 rocketmq-4.7.1]# pwd
/usr/local/software/rocketmq/distribution/target/rocketmq-4.7.1/rocketmq-4.7.1

启动namesrv
[root@112 rocketmq-4.7.1]# nohup sh bin/mqnamesrv &
启动broker
[root@112 rocketmq-4.7.1]# nohup sh bin/mqbroker -n localhost:9876 -c ./conf/broker.conf &
查看启动情况
[root@112 rocketmq-4.7.1]# tail -f nohup.out 
The Name Server boot success. serializeType=JSON
The broker[broker-a, 112.126.78.225:10911] boot success. serializeType=JSON and name server is localhost:9876
Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
The Name Server boot success. serializeType=JSON
The broker[broker-a, 112.126.78.225:10911] boot success. serializeType=JSON and name server is localhost:9876
Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
The Name Server boot success. serializeType=JSON
The broker[broker-a, 112.126.78.225:10911] boot success. serializeType=JSON and name server is localhost:9876

如果都是success说明启动成功，到此已经配置完成，如果需要有可视化的操作界面则请看接下来的rocketmq控制台配置
```

####RocketMQ控制台配置

控制台需要到github上下载，上传到linux上，当然也可以直接在linux机器上使用git命令拉取，需要安装git命令，为了方便，安装包我已经给出，下载地址：https://github.com/apache/rocketmq-externals

```shell
解压
[root@112 software]# unzip rocketmq-externals.zip
进入目录
[root@112 software]# cd rocketmq-externals/rocketmq-console/
同样是maven项目，进行编译打包
[root@112 software]# mvn clean package -Dmaven.test.skip=true
找到打包后的文件进行启动
[root@112 rocketmq-console]# cd target/
使用java命令启动jar
[root@112 target]# nohup java -jar rocketmq-console-ng-2.0.0.jar &
```

如果启动成功，通过浏览器访问http://112.126.78.225:8080/，即可进入rocketmq的控制台界面，ip地址需要指定自己的ip地址，控制台默认端口为8080

###Docker安装

Docker安装是非必须的，但是如果后台代码要部署，则需要安装，项目中的Sleuth/Zipin链路追踪使用的就是docker的方式，如果你希望在浏览器看见各个微服务的调用情况，则需要安装，当然，你也可以通过安装包的形式部署，我是采用的docker形式部署的链路追踪

docker安装对内核版本有要求，docker官方说至少3.8以上，建议3.10以上（ubuntu下要linux内核3.8以上）

查看内核版本

```shell
[root@101 ~]# uname -a
Linux 101.200.203.216 4.18.0-193.14.2.el8_2.x86_64 #1 SMP Sun Jul 26 03:54:29 UTC 2020 x86_64 x86_64 x86_64 GNU/Linux
```

安装需要的软件包

```shell
[root@101 ~]# yum install -y yum-utils device-mapper-persistent-data lvm2
```

设置docker源（阿里仓库）

```shell
[root@101 ~]# yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

查看所有docker版本，并选择特定版本进行安装

```shell
[root@101 ~]# yum list docker-ce --showduplicates | sort -r
[root@101 ~]# yum install docker-ce-18.03.1.ce
```

启动docker并加入开机启动

```shell
[root@101 ~]# systemctl start docker
[root@101 ~]# systemctl enable docker
```

查看版本

```shell
[root@101 ~]# docker version
```

查看详细信息

```shell
[root@101 ~]# docker info
```

配置阿里云镜像加速

```shell
[root@iZ2zeigbwa2dzyxqi6v1d8Z ~]# vim /etc/docker/daemon.json

{
  "registry-mirrors": ["https://gs42lu6e.mirror.aliyuncs.com"]
}

[root@101 ~]# systemctl daemon-reload && systemctl restart docker
```

####基本操作

* 构建容器：docker run -itd --name=mycentos centos:7	
  * -i：表示以交互模式运行容器（让容器的标准输入打开）
  * -d：表示后台运行容器，并返回容器id
  * -t：为容器重新分配一个伪输入终端
  * --name：为容器指定名称
* 查看本地所有的容器：docker ps -a
* 查看本地正在运行的容器：docker ps
* 停止容器：docker stop CONTAINER_ID/CONTAINER_NAME
* 一次性停止所有容器：docker stop $(docker ps -a -q)
* 启动容器：docker start CONTAINER_ID/CONTAINER_NAME
* 重启容器：docker restart CONTAINER_ID/CONTAINER_NAME
* 删除容器：docker rm CONTAINER_ID/CONTAINER_NAME
* 强制删除容器：docker rmi -f CONTAINER_ID/CONTAINER_NAME
* 查看容器详细信息：docker inspect CONTAINER_ID/CONTAINER_NAME

###使用Docker安装zipkin

获取镜像

```shell
[root@101 ~]# docker pull openzipkin/zipkin
```

启动

```shell
[root@101 ~]# docker run --name zipkin -d -p 9411:9411 openzipkin/zipkin
```

使用浏览器访问：http://101.200.203.216:9411/zipkin，如果能进入可视化界面说明配置成功，ip地址为安装docker的机器的地址，端口使用9411

### Nacos安装

将提供的nacos上传到服务器，进行解压

```shell
[root@101 local]# tar -zxvf nacos-server-1.2.0.tar.gz
修改名称
[root@101 local]# mv nacos-server-1.2.0 nacos1.2
进入bin目录
[root@101 local]# cd nacos1.2/nacos/bin/
以单机的模式启动，它是默认以后台运行，因此不需要使用nohup
[root@101 bin]# sh startup.sh -m standalone
关闭nacos
[root@101 bin]# sh shutdown.sh
```

启动访问：http://101.200.203.216:8848/nacos，ip地址为自己nacos地址，账号密码都是nacos

### seata安装

为了方便管理配置，我们会将seata的配置提交到nacos的配置中心，以便在nacos中修改配置后就能够生效，不需要重启seata，且在提交之前一定保证nacos已经启动

官方教程：https://mp.weixin.qq.com/s/2KSidJ72YsovpJ94P1aK1g

注：seata所需要的seata库和undo_log表都在项目中的sql脚本，因此sql脚本不需要管

假设你已经将seata上传到了linux的/usr/local/software目录下

解压

```shell
[root@VM-0-4-centos ~]# cd /usr/local/software/
[root@VM-0-4-centos software]# tar -zxvf seata-server-1.2.0.tar.gz
```

重命名

```shell
[root@VM-0-4-centos software]# mv seata-server-1.2.0 seata
```

添加config.txt配置文件

```shell
[root@VM-0-4-centos software]# cd seata
[root@VM-0-4-centos seata]# vim config.txt
```

config.txt配置文件内容，store.db的连接内容请改成自己的mysql信息

```txt
service.vgroupMapping.my_test_tx_group=default
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://ip地址:3306/seata?useUnicode=true
store.db.user=账号
store.db.password=密码
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

进入bin目录

```shell
[root@VM-0-4-centos seata]# cd bin/
```

创建名为nacos-config.sh的shell文件，将以下shell脚本复制进入

```shell
#!/usr/bin/env bash
# Copyright 1999-2019 Seata.io Group.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at、
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

while getopts ":h:p:g:t:u:w:" opt
do
  case $opt in
  h)
    host=$OPTARG
    ;;
  p)
    port=$OPTARG
    ;;
  g)
    group=$OPTARG
    ;;
  t)
    tenant=$OPTARG
    ;;
  u)
    username=$OPTARG
    ;;
  w)
    password=$OPTARG
    ;;
  ?)
    echo " USAGE OPTION: $0 [-h host] [-p port] [-g group] [-t tenant] [-u username] [-w password] "
    exit 1
    ;;
  esac
done

if [[ -z ${host} ]]; then
    host=localhost
fi
if [[ -z ${port} ]]; then
    port=8848
fi
if [[ -z ${group} ]]; then
    group="SEATA_GROUP"
fi
if [[ -z ${tenant} ]]; then
    tenant=""
fi
if [[ -z ${username} ]]; then
    username=""
fi
if [[ -z ${password} ]]; then
    password=""
fi

nacosAddr=$host:$port
contentType="content-type:application/json;charset=UTF-8"

echo "set nacosAddr=$nacosAddr"
echo "set group=$group"

failCount=0
tempLog=$(mktemp -u)
function addConfig() {
  curl -X POST -H "${contentType}" "http://$nacosAddr/nacos/v1/cs/configs?dataId=$1&group=$group&content=$2&tenant=$tenant&username=$username&password=$password" >"${tempLog}" 2>/dev/null
  if [[ -z $(cat "${tempLog}") ]]; then
    echo " Please check the cluster status. "
    exit 1
  fi
  if [[ $(cat "${tempLog}") =~ "true" ]]; then
    echo "Set $1=$2 successfully "
  else
    echo "Set $1=$2 failure "
    (( failCount++ ))
  fi
}

count=0
for line in $(cat $(dirname "$PWD")/config.txt | sed s/[[:space:]]//g); do
  (( count++ ))
	key=${line%%=*}
    value=${line#*=}
	addConfig "${key}" "${value}"
done

echo "========================================================================="
echo " Complete initialization parameters,  total-count:$count ,  failure-count:$failCount "
echo "========================================================================="

if [[ ${failCount} -eq 0 ]]; then
	echo " Init nacos config finished, please start seata-server. "
else
	echo " init nacos config fail. "
```

给文件添加权限

```shell
[root@VM-0-4-centos bin]# chmod 777 nacos-config.sh 
```

执行shell脚本，将配置由nacos配置中心接管，注：shell脚本使用了curl，运行前需要保证自己linux有这个命令，我自己的云服务器默认没有安装，如果没有，请先安装curl命令

-h：nacos的ip地址		-p：nacos的端口，默认为8848

```shell
[root@VM-0-4-centos bin]# sh nacos-config.sh -h 101.200.203.216 -p 8848 -g SEATA_GROUP
```

如果推送成功，在nacos的配置管理中将会出现许多关于seata的配置文件

启动seata服务端（注：如果是远程服务器，-h参数必须传入seata公网ip地址，否则程序会报异常）

```shell
[root@VM-0-4-centos bin]# sh seata-server.sh -p 8091 -h 132.232.28.130
```

###sentinel安装

sentinel使用比较简单，只需要运行一个jar包就行，但是我放在云服务器上启动运行，即便我url请求了很多次，sentinel仍然无法获取到请求的信息，无法对其进行流控等操作，因此我将其放在了本地运行，就能够正常检测到。

启动sentinel

在sentinel-dashboard.jar所在位置打开cmd，执行：java -jar sentinel-dashboard.jar即可

启动成功后访问：http://localhost:8080/，账号密码都是sentinel

###微服务项目完整启动步骤

* 将项目提供的sql脚本在mysql中先执行

* 启动nacos（必须项）

  ```shell
  [root@101 ~]# cd /usr/local/nacos1.2/nacos/bin/
  [root@101 bin]# sh startup.sh -m standalone
  ```

* 启动zipkin（非必须，不启动将无法对链路进行分析，但是微服务项目能够正常运行）

  ```shell
  [root@101 /]# docker run --name zipkin -d -p 9411:9411 openzipkin/zipkin
  ```

* 启动rocketmq（必须项）

  ```shell
  [root@112 ~]# cd /usr/local/software/rocketmq/distribution/target/rocketmq-4.7.1/rocketmq-4.7.1/
  [root@112 rocketmq-4.7.1]# nohup sh bin/mqnamesrv &
  [root@112 rocketmq-4.7.1]# nohup sh bin/mqbroker -n localhost:9876 -c ./conf/broker.conf &
  ```

  * 启动控制台（非必须，不启动将无法通过可视化界面查看MQ的情况）

    ```shell
    [root@112 ~]# cd /usr/local/software/rocketmq-externals-master/rocketmq-console/target/
    [root@112 target]# nohup java -jar rocketmq-console-ng-2.0.0.jar &
    ```

* 启动mysql（必须项，前文已经配置了开机自启）

* 启动redis（必须项，前文已经配置了开机自启）

* 启动seata（非必须，不启动不影响项目运行，但是无法实现分布式事务）

  ```shell
  [root@VM-0-4-centos ~]# cd /usr/local/software/seata/seata/bin/
  [root@VM-0-4-centos bin]# nohup sh seata-server.sh -h 132.232.28.130 -p 8091 &
  ```

* 启动sentinel（非必须，不启动不影响项目运行，但是无法进行流控降级等操作）

  sentinel在远程服务器上使用无法准确的获取到项目的数据，因此我开发环境部署在自己windows系统上，直接运行jar包，在sentinel-dashboard.jar所在位置打开cmd，执行：java -jar sentinel-dashboard.jar即可

* 启动网关微服务

* 启动用户微服务

* 启动订单微服务

* 启动订单微服务

以上所有配置的文件都再tools目录里面有提供，如果不想下载的可以直接上传到linux上使用

至此，该项目的后端服务以及搭建完成，可以去启动前端和安卓端进行使用了，如启动该有问题，请联系QQ：2243756824