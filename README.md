# springbootStudy
## 针对SpringBoot的学习笔记
### SpringBoot读取Yml配置文件的几种方式
- @Value 的方式（该方式最简单）
```yaml
    server:
      port:8081
```
我们在代码中这样取值
```java
    @Value("${server.port}")
    public String port;
```
注：此处的port所在的类需要时一个组件，如果是实体类需要加上@Component
- 第二种读取方式ConfigrationProperities
```yaml
student:
  age: 18
  name: ken
```
```java
@Compenent
@ConfigrationProperties(prefix="student")
public class Student
{
    private int age;
    private String name;
    public Integer getAge()
    {
        return age;
    }
    public void setAge(Integer age){
        this.age=age;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }

}
```
使用ConfigrationProperities,需要配置一个prefix（前缀）参数，写上对应的key即可。
- 第三种读取方式@Environment
```yaml
student:
  book: kofu
```
java代码
```java
public class show
{
    @AutoWired
    private Environment env;
    
    @Test
    public void Test()
    {
        System.out.println(env.getProperty(student.book));
    }
}
```
### springboot原理
#### 1、自动装配
pom.xml
- spring-boot-dependencies:核心依赖在父工程里(spring-boot-stater-parent)
我们在引入springboot依赖时，不需要指定版本，就是因为有这些版本仓库

启动器（stater）
````xml
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter</artifactId>
 </dependency>
````
- 启动器就是springboot的启动场景，需要什么场景就加入什么依赖即可。如web场景
```xml
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```
- 我们要是用什么功能，只需要添加对应的依赖即可。目前启动器有以下这些：
````markdown
spring-boot-starter

Core starter, including auto-configuration support, logging and YAML

spring-boot-starter-activemq

Starter for JMS messaging using Apache ActiveMQ

spring-boot-starter-amqp

Starter for using Spring AMQP and Rabbit MQ

spring-boot-starter-aop

Starter for aspect-oriented programming with Spring AOP and AspectJ

spring-boot-starter-artemis

Starter for JMS messaging using Apache Artemis

spring-boot-starter-batch

Starter for using Spring Batch

spring-boot-starter-cache

Starter for using Spring Framework’s caching support

spring-boot-starter-data-cassandra

Starter for using Cassandra distributed database and Spring Data Cassandra

spring-boot-starter-data-cassandra-reactive

Starter for using Cassandra distributed database and Spring Data Cassandra Reactive

spring-boot-starter-data-couchbase

Starter for using Couchbase document-oriented database and Spring Data Couchbase

spring-boot-starter-data-couchbase-reactive

Starter for using Couchbase document-oriented database and Spring Data Couchbase Reactive

spring-boot-starter-data-elasticsearch

Starter for using Elasticsearch search and analytics engine and Spring Data Elasticsearch

spring-boot-starter-data-jdbc

Starter for using Spring Data JDBC

spring-boot-starter-data-jpa

Starter for using Spring Data JPA with Hibernate

spring-boot-starter-data-ldap

Starter for using Spring Data LDAP

spring-boot-starter-data-mongodb

Starter for using MongoDB document-oriented database and Spring Data MongoDB

spring-boot-starter-data-mongodb-reactive

Starter for using MongoDB document-oriented database and Spring Data MongoDB Reactive

spring-boot-starter-data-neo4j

Starter for using Neo4j graph database and Spring Data Neo4j

spring-boot-starter-data-r2dbc

Starter for using Spring Data R2DBC

spring-boot-starter-data-redis

Starter for using Redis key-value data store with Spring Data Redis and the Lettuce client

spring-boot-starter-data-redis-reactive

Starter for using Redis key-value data store with Spring Data Redis reactive and the Lettuce client

spring-boot-starter-data-rest

Starter for exposing Spring Data repositories over REST using Spring Data REST

spring-boot-starter-data-solr

Starter for using the Apache Solr search platform with Spring Data Solr

spring-boot-starter-freemarker

Starter for building MVC web applications using FreeMarker views

spring-boot-starter-groovy-templates

Starter for building MVC web applications using Groovy Templates views

spring-boot-starter-hateoas

Starter for building hypermedia-based RESTful web application with Spring MVC and Spring HATEOAS

spring-boot-starter-integration

Starter for using Spring Integration

spring-boot-starter-jdbc

Starter for using JDBC with the HikariCP connection pool

spring-boot-starter-jersey

Starter for building RESTful web applications using JAX-RS and Jersey. An alternative to spring-boot-starter-web

spring-boot-starter-jooq

Starter for using jOOQ to access SQL databases. An alternative to spring-boot-starter-data-jpa or spring-boot-starter-jdbc

spring-boot-starter-json

Starter for reading and writing json

spring-boot-starter-jta-atomikos

Starter for JTA transactions using Atomikos

spring-boot-starter-jta-bitronix

Starter for JTA transactions using Bitronix. Deprecated since 2.3.0

spring-boot-starter-mail

Starter for using Java Mail and Spring Framework’s email sending support

spring-boot-starter-mustache

Starter for building web applications using Mustache views

spring-boot-starter-oauth2-client

Starter for using Spring Security’s OAuth2/OpenID Connect client features

spring-boot-starter-oauth2-resource-server

Starter for using Spring Security’s OAuth2 resource server features

spring-boot-starter-quartz

Starter for using the Quartz scheduler

spring-boot-starter-rsocket

Starter for building RSocket clients and servers

spring-boot-starter-security

Starter for using Spring Security

spring-boot-starter-test

Starter for testing Spring Boot applications with libraries including JUnit Jupiter, Hamcrest and Mockito

spring-boot-starter-thymeleaf

Starter for building MVC web applications using Thymeleaf views

spring-boot-starter-validation

Starter for using Java Bean Validation with Hibernate Validator

spring-boot-starter-web

Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container

spring-boot-starter-web-services

Starter for using Spring Web Services

spring-boot-starter-webflux

Starter for building WebFlux applications using Spring Framework’s Reactive Web support

spring-boot-starter-websocket

Starter for building WebSocket applications using Spring Framework’s WebSocket support
````

#### 2、自动装配的原理
1）、SpringBoot启动时会加载大量的自动配置类；
2）、我们看我们需要的功能有没有在SpringBoot默认写好的自动配置类中；
3）、我们再来看这个自定配置类中到底配置了那些组件；（只要我们用的组件存在在其中，我们就不用手动配置了）
4）、给容器中自动配置类添加组件的时候，会从properties类中获取对应的属性。我们只需要在配置文件中指定这些属性的值即可
xxxAutoConfigurartion：自动配置类；给容器添加组件
xxxProperties：封装配置文件中相关属性

#### 主程序
springboot 所有自动配置都是在启动的时候，扫描并加载：sprign.factories所有的自动配置类都在这里面。但是不一定生效，要判断条件是否成立，只要导入了对应的starter,就有对应的启动器了。有了启动器我们自动装配就会生效，然后就配置成功。
1）、springboot在启动的时候，动路径/META-INF/spring.factories获取指定的值。
2）、将这些自动配置的类导入容器中，自动配置就会生效。
3）、以前我们需要手动配置的东西，现在springboot帮我们做了。

