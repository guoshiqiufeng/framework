# core-boot-starter

#### 介绍
通用快速配置框架

#### 模块

| 名称         | 模块     |
| ------------ | -------- |
| Mysql核心配置 | MysqlCoreConfiguration |

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>io.github.guoshiqiufeng</groupId>
    <artifactId>core-boot-starter</artifactId>
    <version>1.3.0</version>
</dependency>
```

#### 使用说明

##### **MysqlCoreConfiguration**

注意：

1. 本模块会根据配置自动装配dataSource,配置文件可通过```app.properties```、```application.properties```、```application.yml```三个配置文件进行单参数覆盖，即```application.yml```中配置的```mysql.core```下的配置会覆盖```application.properties```中的```mysql.core```下的配置，同理也会覆盖```app.properties```

2. 若项目本身引用了jdbc自动注入相关如```mybatis-plus```等，则需要在```application.yml```或```application.properties```进行不自动化配置相关模块，配置如下

   ```yml
   spring:
     # 指定默认不自动化配置相关模块
     autoconfigure:
       exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
   ```

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
mysql:
  core:
    enabled: true
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| mysql.core.enabled             | 是否启用自动装配               | 默认为false                              |
| mysql.core.jdbcUrl             | jdbc地址                       |                                          |
| mysql.core.jdbcUserName        | jdbc用户名                     |                                          |
| mysql.core.jdbcPassword        | jdbc密码                       |                                          |
| mysql.core.reversePassword     | 是否开启反转密码               | 默认为false,开启后jdbcPassword需倒序输入 |
| mysql.core.jdbcDriver          | 驱动程序                       |                                          |
| mysql.core.maximumPoolSize     | 最大连接数                     | 默认50                                   |
| mysql.core.minimumIdle         | 最小连接数                     | 默认5                                    |
| mysql.core.idleTimeout         | 连接状态的最大时长（毫秒）     | 默认600000                               |
| mysql.core.maxLifetime         | 连接的生命时长（毫秒）         | 默认1800000                              |
| mysql.core.connectionTimeout   | 接池分配连接的最大时长（毫秒） | 默认30000                                |
| mysql.core.connectionTestQuery | 测试查询sql                    | 默认"SELECT 1"                           |

**注意：**1.3.0版本只支持 Hikari 连接池