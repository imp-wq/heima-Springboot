### 分模块开发与设计

* maven引用其他模块：

  可以将项目的domain（pojo），dao等放到单独的module中，然后在整体项目中进行引用。

  1. 将被引用模块在maven lifecycle中进行install，加入到maven的repository中。
  2. 在项目的dependencies中添加引用模块的坐标，并进行import。

### 依赖管理

* 依赖具有传递性。

  * 直接依赖
  * 间接依赖

* 依赖冲突

  * 特殊优先：同级配置相同资源的不同版本，后配置的覆盖先配置的。
  * 路径优先：相同资源的依赖层级越深，优先级越低。
  * 声明优先

* 可以通过maven的show dependencies功能看到依赖之间的关系。

* 可选依赖和排除依赖：

  * 可选依赖：通过在dependency节点中设置配置项optional为true，设置为可选依赖。该项目被引用时，会隐藏可选依赖，从而避免在引用该项目时产生依赖冲突问题。

    可选依赖用于隐藏当前工程所依赖的资源，隐藏后对应资源将不具有依赖传递性。

    ```xml
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
        <optional>true</optional>
    </dependency>
    ```

  * 排除依赖：可以在引用项目时，通过exclusions节点排除不想要的依赖。排除以来时无需指定版本。

    隐藏当前资源的依赖关系。

    ```xml
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    ```

### 聚合

maven项目中通常需要创建一个聚合工程，专门用于管理其他工程。

* 聚合工程的打包方式需要设置为pom

  ```xml
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
  
    <groupId>com.itniuma</groupId>
    <artifactId>maven_01_parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
  ```

* 通过modules设置管理的模块

  ```xml
  <modules>
      <module>../maven_03_pojo</module>
  </modules>
  ```

* 对聚合工程进行编译等操作，会按依赖顺序对所有管理的模块进行编译。

### 继承

两个工程间可以存在继承关系，子工程继承父工程的配置信息，常见于依赖关系的继承。可用于简化配置和减少版本冲突。

* 在子工程中通过parent节点选择继承的父工程

  relative path节点选择父工程的pom.xml文件路径。

  ```xml
   <parent>
          <groupId>com.itniuma</groupId>
          <artifactId>maven_01_parent</artifactId>
          <version>1.0-SNAPSHOT</version>
          <relativePath>../maven_01_parent/pom.xml</relativePath>
  </parent>
  ```

* 可以通过dependency management节点配置不强制子工程继承的模块，只提供给子工程使用。

### 属性

pom.xml中可以定义属性，即保存变量的字段，用于对版本号等进行统一管理

* 定义属性：在properties节点中定义属性，标签名即为属性名称。

  ```xml
  <properties>
      <spring.version>5.2.10.RELEASE</spring.version>
  </properties>
  ```

* 使用属性：通过`${属性名}`的方式使用属性。

  ```xml
  <version>${spring.version}</version>
  ```

  