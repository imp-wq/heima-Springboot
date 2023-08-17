## 创建工程

* idea中创建sprint boot工程必须联网进行。
* package：默认会加上artifact id，一般设置为同group id。
* dependencies：添加Web->Spring Web.
* Spring Boot程序包含的基础文件：
  * pom.xml
  * Application类
* 创建项目的方式：
  * idea：new module-> spring initializer
  * spring官网：[Spring Initializr](https://start.spring.io/) spring initializer，生成压缩文件下载

### idea隐藏目录/文件

* setting->editor->file type->ignored files and folders中配置idea打开项目时忽略的文件和文件夹。

### idea compare files

* idea中右键两个文件，可以比较文件的不同之处。

### parent

* 在spring boot的pom.xml中继承的parent中，定义了若干依赖管理，通过maven的properties和dependencyManagement对许多使用的依赖版本进行管理，避免版本冲突。

### starter

* 定义了当前项目所使用的依赖坐标，达到减少依赖配置的目的。

### 引导类

引导类时boot工程的入口，运行main方法启动项目。

* spring默认扫描引导类的所在包及其子包。(`@ComponentScan`)
* boot工程运行后会初始化spring容器，扫描引导类所在包加载bean。

## 基础配置

* pom.xml中的name和description节点：
  * 这两个节点可以删除。
  * 在maven插件中，如果有name节点，会显示工程的name，否则显示工程的artifact id。

* spring boot工程的基础配置在application.properties文件中进行。

  ```properties
  server.port=80
  
  # banner相关配置
  spring.main.banner-mode=off
  #spring.banner.location=banner.txt
  
  # 日志
  # 日志级别
  logging.level.root=debug
  ```

* 官方文档：[Common Application Properties (spring.io)](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties)

* boot配置的3种格式：

  * properties
  * yml
  * yaml

* 多个不同类型的配置文件存在时，配置属性共存叠加并相互覆盖。

  覆盖顺序：properties>yml>yaml

### yaml文件格式

* 后缀名为yml（主流）或yaml。

* 大小写敏感

* 使用缩进表示层级关系，只允许空格缩进（不允许使用tab`\t`）

* 属性值（冒号后）需要添加空格。

* `#`表示注释

* 属性不能重复

* 数据类型：

  * string加不加引号都可以，除非中间有空格等情况。
    * **双**引号包裹的string中转义符会生效。不加双引号`\t`会直接按字符串处理，加了引号会解析为制表符。

  * `~`表示null
  * 日期：`yyyy-MM-dd`格式，日期时间有特殊格式。

* 数组：

  ```yaml
  likes:
    - game
    - music
    - sleep
  ```

  ```yaml
  likes: [game, music, sleep]
  ```

* 对象数组：

  ```yaml
  user:
    - name: zhangsan
      age: 18
    - name: lisi
      age: 17
  ```
  ```yaml
  user: [{name: zhangsan, age: 18},{name: lisi, age: 17}]
  ```

* 变量引用：`${变量名}`

  ```yaml
  baseDir: c:\windows
  tmpDir: ${baseDir}\temp
  ```

* spring读取resources中的yaml文件：

  ```java
  @Value("${user}")
  private String user;
  
  @Value("${hobbies[1]}")
  private String hobby;
  
  @Value("${friends[0].name}")
  private String friendName;
  
  @GetMapping
  public String getRequest() {
      System.out.println("accept get request");
      return String.format("accept get request\t" +
                           "username: %s\t" +
                           "hobby:%s\t" +
                           "friendName:%s"
                           , this.user, this.hobby, this.friendName);
  }
  ```

* Environment对象：yaml文件可以被封装为一个Environment对象，通过Autowired一次性读取，再通过getProperty方法读取文件中的属性。

  ```java
  @Autowired
  private Environment environment;
  
  public String getRequest() {
      System.out.println("accept get request");
      System.out.println(this.environment.getProperty("baseDir"));
      return String.format("accept get request\t" +
                           "username: %s\t" +
                           "hobby:%s\t" +
                           "friendName:%s"
                           , this.user, this.hobby, this.friendName);
  }
  ```

* 将yaml指定数据加载到java class：

  1. 定义java类数据模型，用于封装yaml文件中对应数据，并将类注册为spring bean。

     属性名必须与yaml文件中对应。

  2. 通过`@ConfigurationProperties`注解指定加载数据的yaml属性，即指定prefix。

     ```java
     
     @Component
     @ConfigurationProperties("datasource")
     public class MyDataSource {
         private String drive;
         private String url;
         private String username;
         private String password;
     
         @Override
         public String toString() {
             return "MyDataSource{" +
                 "drive='" + drive + '\'' +
                 ", url='" + url + '\'' +
                 ", username='" + username + '\'' +
                 ", password='" + password + '\'' +
                 '}';
         }
     	...
     }
     ```


