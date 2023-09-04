## 打包

* idea->maven->package，打成jar包后通过`java -jar 程序名`运行。

* 打包默认会执行测试类中的代码，可以通过在maven跳过test周期来跳过测试。

* spring boot打包依赖于打包插件，需要有打包插件生成的jar包才能正常运行

  ```xml
  <build>
      <plugins>
          <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
      </plugins>
  </build>
  ```

* linux中用户开发程序一般放在`/usr/local`中，或`/home`中。


## linux部署

* RHEL虚拟机mysql密码：Mysql_123.

* 虚拟机网络连接选择nat模式即可，用`ip addr`查询虚拟机ip地址。

* 注意java版本

  * yum命令无法直接安装jdk 17

  * 通过wget下载安装jdk17，并在`/etc/profile`中加入环境变量。

    ```txt
    export JAVA_HOME=/usr/lib/jvm/jdk-17.0.8
    export PATH=$PATH:$JAVA_HOME/bin
    export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    ```

  * 通过`alternatives --install`加入jdk版本。

* 注意关闭linux 80端口的防火墙。

  ```sh
  [lisi@localhost ~]$ firewall-cmd --add-port=80/tcp --permanent
  success
  [lisi@localhost ~]$ firewall-cmd --reload 
  success
  ```

### Mysql

* 安装mysql：

  * 安装完毕通过`start mysqld.service`开启服务
  * 通过`grep 'temporary password' /var/log/mysqld.log`查看初始密码
  * 登录：`mysql -uroot -p`

* 主机navicat连接mysql：

  * 主机通过`mysql -u root -h 192.168.0.50 -p`在命令行中连接。

  * linux中mysql需要设置允许远程连接：

    通过更改mysql.user表来设置远程连接权限，%代表允许所有ip。

    ```mysql
    mysql> use mysql
    Reading table information for completion of table and column names
    You can turn off this feature to get a quicker startup with -A
    
    Database changed
    mysql> update user set host='%' where user='root';
    Query OK, 1 row affected (0.00 sec)
    Rows matched: 1  Changed: 1  Warnings: 0
    
    mysql> select user,host from mysql.user;
    +------------------+-----------+
    | user             | host      |
    +------------------+-----------+
    | root             | %         |
    | mysql.infoschema | localhost |
    | mysql.session    | localhost |
    | mysql.sys        | localhost |
    +------------------+-----------+
    4 rows in set (0.00 sec)
    
    mysql> flush privileges;
    Query OK, 0 rows affected (0.00 sec)
    
    ```

  * linux需要保证3306端口打开。

    查看mysql使用的端口。

    ```mysql
    mysql> show global variables like 'port'-> ;
    +---------------+-------+
    | Variable_name | Value |
    +---------------+-------+
    | port          | 3306  |
    +---------------+-------+
    1 row in set (0.01 sec)
    ```

* mysql设置密码规则：

  ```mysql
  mysql> show global variables like 'validate%';
  +-------------------------------------------------+-------+
  | Variable_name                                   | Value |
  +-------------------------------------------------+-------+
  | validate_password.changed_characters_percentage | 0     |
  | validate_password.check_user_name               | ON    |
  | validate_password.dictionary_file               |       |
  | validate_password.length                        | 6     |
  | validate_password.mixed_case_count              | 0     |
  | validate_password.number_count                  | 0     |
  | validate_password.policy                        | LOW   |
  | validate_password.special_char_count            | 0     |
  +-------------------------------------------------+-------+
  8 rows in set (0.01 sec)
  
  mysql> set global validate_password.length=6;
  Query OK, 0 rows affected (0.00 sec)
  
  mysql> set global validate_password.length=5;
  Query OK, 0 rows affected (0.00 sec)
  
  mysql> ALTER USER 'root'@'%' IDENTIFIED BY 'mysql';
  Query OK, 0 rows affected (0.04 sec)
  ```

## 临时属性

* 命令行运行服务时，可以通过`--server.port=8080`的形式添加临时属性，在本次运行时暂时覆盖原属性。

  多个临时属性使用空格分隔。

* 各种配置中的属性优先级：https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html
* 临时属性通过springboot启动类`SpringApplication.run`方法的第二个参数args接收，如果不想用户使用临时属性可以不传这个参数。

## 多环境

* 可以通过`---`分割配置文件，为生产/测试/上线等不同环境配置不同的属性值。

  ```yaml
  spring:
    profiles:
      active: dev # 使用生产环境
  # 公共配置
  ...
  ---
  spring:
    config:
      activate:
        on-profile: dev # 生产环境名
  
  # 生产环境的配置
  server:
    port: 80
    
  ---
  spring:
    config:
      activate:
        on-profile: test # 测试生产环境名
  # 测试环境的配置
  server:
    port: 81
  ```


* 也可以将不同环境的配置放在单独的文件中，文件名命名为`application-环境名.yml`的形式。

  主配置文件中再通过`spring.profiles.active`指定当前环境。

  配置文件中无需再通过`spring...on-profile`指定环境名。

  * 公共配置放在`application.yml`中
  * 冲突配置放在各个`application-环境名.yml`中

## 自定义配置文件

* 可以通过临时属性`--spring.config.name="文件名"`指定配置文件，注意这里文件名不加后缀名。

  `--spring.config.location=classpath:/my-config.yml`

## 分组配置

* 可以将配置文件按功能分为不同模块，放进单独的配置文件中。

  * 如dev环境的MVC，DB配置分别放入`application-devMVC.yml`,`application-devDB.yml`文件中。

  * 主配置文件中通过`spring.profiles.include`加入各个模块文件。

  * 用group替代include，为不同环境指定各自的模块。

```yaml
spring:
	profiles:
		active: dev
		group:
			"dev": devDB,devMVC
			"pro": proDB,proMVC
```

