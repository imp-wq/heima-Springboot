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

### 临时属性