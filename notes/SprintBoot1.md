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

