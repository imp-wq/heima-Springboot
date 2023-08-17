## 整合junit

* 无需进行额外配置，通过`@SpringBootTest`注解创建测试类。

* 默认测试类在引导类的同一个包或子包中。如果不在，需要手动通过classes指定引导类。

  ```java
  @SpringBootTest(classes = Springboot04JunitApplication.class)
  class Springboot04JunitApplicationTests {
  
      @Autowired
      private BookDao bookDao;
  
      @Test
      void contextLoads() {
          bookDao.save();
      }
  
  }
  ```


## 整合MyBatis

1. 导入对应starter

   Spring Initilizer->SQL->MyBatis Framework & MySQL Driver

2. 在application.yml中通过spring.datasource对象配置数据库链接信息

   ```yaml
   spring:
     datasource:
       driver-class-name: com.mysql.jdbc.Driver
       url: jdbc:mysql://localhost:3306/mybatis
       username: root
       password: mysql
   ```

3. 即可通过`@Mapper`注解开发dao层

## 整合MyBatis Plus

* mybatis plus由于是国人开发的，因此spring initializer中没有。
* 可以在创建时只导入MySQL Driver的坐标，再通过[Maven Repository: com.baomidou » mybatis-plus-boot-starter » 3.5.3.1 (mvnrepository.com)](https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter/3.5.3.1)手动导入坐标。

* mybatis plus基本使用：

  * dao层中无需自己写sql语句，直接继承BaseMapper，使用其方法。

    ```java
    @Mapper
    public interface UserDao extends BaseMapper<User> {
    }
    ```

  * mybatis plus默认要求表名与pojo名称相同。
  
  * 分页需要添加mybaties plus拦截器

## 整合Druid

* 也需要手动导入坐标

* 配置方式：

  * 在type中配置：

    ```yaml
    spring:
      datasource:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/mybatis
        username: root
        password: mysql
        type: com.alibaba.druid.pool.DruidDataSource
    ```

  * 配置druid对象：

    ```yaml
    spring:
      datasource:
        druid:
          driver-class-name: com.mysql.jdbc.Driver
          url: jdbc:mysql://localhost:3306/mybatis
          username: root
          password: mysql
    ```


## Demo：SSMP

* service层和dao层的方法在命名上有所不同：

  * service层侧重业务，如`login`
  * dao层侧重数据，如`selectUserByUsernameAndPassword`

* service层接口查询、删除等方法返回类型一般为boolean，代表操作是否成功。

  dao层mybatis默认返回的是影响行计数。

* 单体项目中，网页文件放在resource/static目录中，运行前最好先执行一下maven的clean生命周期。

* 统一的异常处理类ExceptionAdvice

  ```java
  @RestControllerAdvice
  public class ExceptionAdvice {
      // 拦截所有异常信息
      @ExceptionHandler
      public Result doException(Exception exception) {
          // 控制台打印异常信息
          exception.printStackTrace();
          return new Result(false, "服务器故障，请稍后再试");
      }
  }
  ```

  

### lombok基本使用

* @Getter, @Setter为类的所有字段添加getter和setter。
* @Data 为所有字段添加getter和setter，并提供toString, hashcode, equals方法。

